package com.adobe.aem.guides.wknd.core.models;

public interface NewsArticle {
    String getTitle();
    String getDescription();
    String getPublishDate();
    String getArticleImage();
    String[] getArticleList();
    boolean isEmpty();
}
