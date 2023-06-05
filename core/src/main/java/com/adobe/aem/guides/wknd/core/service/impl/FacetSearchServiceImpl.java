package com.adobe.aem.guides.wknd.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.service.FacetSearchService;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Component(service = FacetSearchService.class, immediate = true)
public class FacetSearchServiceImpl implements FacetSearchService  {
    private static final Logger LOG = LoggerFactory.getLogger(FacetSearchServiceImpl.class);
    @Reference
    QueryBuilder queryBuilder;
    @Reference
    ResourceResolverFactory resourceResolverFactory;
    public Map<String,String> createTextSearchQuery(String[] searchTags, String searchpath)
    { 
        Map<String, String> predicate = new HashMap<>();
            int g = 1;
            predicate.put("path",searchpath);
            predicate.put("type", "cq:Page");
            for (int i = 0; i < searchTags.length; i++) {  
                if (!"".equals(searchTags[i])) {
                  predicate.put("group."+Integer.toString(g)+"_property", "jcr:content/@cq:customtags");  
                  predicate.put("group."+Integer.toString(g)+"_property.value", searchTags[i]);   
                  g++;
                }
            }
            predicate.put("group.p.and","true");
            predicate.put("p.limit", Long.toString(-1));
            return predicate;
    }
    @Override
    public JsonObject searchresult(String[] searchTags , String searchpath, SlingHttpServletRequest request) {
        LOG.info("\n ------SEARCH RESULT ----- ");
        JsonObject searchResult = new JsonObject();
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query= queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(searchTags, searchpath)), session);
            query.setStart(0);
            SearchResult result = query.getResult();
            long totalResults = result.getTotalMatches();
           
            searchResult.addProperty("totalresult",totalResults);

            List<Hit> hits = result.getHits();
            JsonArray resultArray = new JsonArray();
            for (Hit hit : hits) { 
                Page page = hit.getResource().adaptTo(Page.class);
                JsonObject resulObject = new JsonObject();
                resulObject.addProperty("title", page.getTitle());
                resulObject.addProperty("path", page.getPath());
                resultArray.add(resulObject);
                LOG.info("\n Page {} ", page.getPath());
            }
            searchResult.add("results", resultArray);
        } catch (Exception e) {
            LOG.info("\n ----ERROR-----{}", e.getMessage());
        }
        return searchResult;
    }
    
}
