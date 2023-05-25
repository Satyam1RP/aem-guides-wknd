package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.aem.guides.wknd.core.models.FacetSearch;
import com.day.cq.tagging.Tag;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { FacetSearch.class }, resourceType = {
    FacetSearchImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FacetSearchImpl implements FacetSearch {
    private static final Logger LOG = LoggerFactory.getLogger(FacetSearchImpl.class);
    protected static final String RESOURCE_TYPE = "wknd/components/facetsearch";

    @Inject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String rootPath;

    @ValueMapValue
    private String tagPath;

    @ValueMapValue
    private String headerText;

    List<Map<String,String>> industryList =  new ArrayList<>();
    List<Map<String,String>> sectorList =  new ArrayList<>();
    List<Map<String,String>> countryList =  new ArrayList<>();

    @PostConstruct
    public void init(){
        LOG.info("inside model");
        Resource tagResource = resourceResolver.getResource(tagPath);
        String namespace = tagResource.getName();
        if (tagResource != null) {
            Iterator<Resource> children = tagResource.listChildren();
            while (children.hasNext()) {
                Resource child = children.next();
                String parentTag = child.getName();
                Iterator<Resource> parentTags = child.listChildren();
                while (parentTags.hasNext()) {
                    Resource subChild = parentTags.next();
                    Tag tag = subChild.adaptTo(Tag.class);
                    Map<String,String> map = new HashMap<>();
                    if (child.getName().equals("industry")) {
                        map.put("tagName", tag.getName());
                        map.put("tagPath", namespace+":"+parentTag+"/"+tag.getName());
                        industryList.add(map); 
                    }
                    if (child.getName().equals("sector")) {
                        map.put("tagName", tag.getName());
                        map.put("tagPath", namespace+":"+parentTag+"/"+tag.getName());
                        sectorList.add(map);
                    }
                    if (child.getName().equals("country")) {
                        map.put("tagName", tag.getName());
                        map.put("tagPath", namespace+":"+parentTag+"/"+tag.getName());
                        countryList.add(map);    
                    }
                }   
            }   
        }
    }
    @Override
    public String getRootPath(){
       return rootPath; 
    }
    @Override
    public String getHeaderText() {
        return headerText;
    }  
    @Override
    public List<Map<String, String>> getIndustryList() {
       return industryList;
    }
    @Override
    public List<Map<String, String>> getSectorList() {
        return sectorList;
    }
    @Override
    public List<Map<String, String>> getCountryList() {
       return countryList;
    }
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(rootPath)&&StringUtils.isBlank(headerText)&&StringUtils.isBlank(tagPath);
    }
}
