package com.adobe.aem.guides.wknd.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.aem.guides.wknd.core.models.NewsArticle;
import com.adobe.cq.dam.cfm.ContentFragment;


@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { NewsArticle.class }, resourceType = {
    NewsArticleImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsArticleImpl implements NewsArticle {
    private static final Logger LOG = LoggerFactory.getLogger(NewsArticleImpl.class);
    protected static final String RESOURCE_TYPE = "wknd/components/newsarticle";
    protected static final String TITLE_FIELD = "title";
    protected static final String DESCRIPTION_FIELD = "description";
    protected static final String PUBLISH_DATE_FIELD = "publishDate";
    private String title;
    private String description;
    private String publishDate;

    @Inject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String newsArticle;
    private Boolean empty;

    @PostConstruct
    public void init(){
        LOG.info("DESCRIPTION_FIELD");
        empty = true;
        Resource newsArticleResource = resourceResolver.getResource(newsArticle);
        if (newsArticleResource != null) {
           ContentFragment contentFragment = newsArticleResource.adaptTo(ContentFragment.class);           
            title = contentFragment.getElement(TITLE_FIELD).getContent();
            description = contentFragment.getElement(DESCRIPTION_FIELD).getContent();   
            publishDate = contentFragment.getElement(PUBLISH_DATE_FIELD).getContent(); 
            empty = false;
        }
    }
    @Override
    public String getTitle() {
        return title;
      
    }
    @Override
    public String getDescription() {
        return description;
        
    }
    @Override
    public String getPublishDate() {
        return publishDate;
        
    }
    @Override
    public boolean isEmpty() {
       return empty;
    }
}
