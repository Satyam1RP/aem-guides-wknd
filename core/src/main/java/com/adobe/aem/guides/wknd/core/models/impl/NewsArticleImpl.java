package com.adobe.aem.guides.wknd.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.aem.guides.wknd.core.models.NewsArticle;
import com.adobe.cq.dam.cfm.ContentFragment;
@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { NewsArticle.class }, resourceType = {
    NewsArticleImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsArticleImpl implements NewsArticle {
    protected static final String RESOURCE_TYPE = "wknd/components/newsarticle";
    protected static final String TITLE_FIELD = "title";
    protected static final String DESCRIPTION_FIELD = "description";
    protected static final String PUBLISH_DATE_FIELD = "publishDate";
    protected static final String ARTICLE_IMAGE = "articlePicture";
    protected static final String ARTICLE_LIST = "articleList";

    private String title;
    private String description;
    private String publishDate;
    private String imagePath;
    private String[] articleList;

    @Inject
    private ResourceResolver resourceResolver;
    @ValueMapValue
    private String newsArticle;
    private Boolean empty;

    @PostConstruct
    public void init(){
        empty = true;
        Resource newsArticleResource = resourceResolver.getResource(newsArticle);
        if (newsArticleResource != null) {
           ContentFragment contentFragment = newsArticleResource.adaptTo(ContentFragment.class);           
            title = contentFragment.getElement(TITLE_FIELD).getContent();
            description = contentFragment.getElement(DESCRIPTION_FIELD).getContent();   
            publishDate = contentFragment.getElement(PUBLISH_DATE_FIELD).getContent();
            articleList =  contentFragment.getElement(ARTICLE_LIST).getValue().getValue(String[].class);
            imagePath  =  contentFragment.getElement(ARTICLE_IMAGE).getContent();
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
    public String getArticleImage() {
     return imagePath;
    }
    @Override
    public String[] getArticleList() {
       return articleList;
    }
    @Override
    public boolean isEmpty() {
       return empty;
    }
   
}
   
    
