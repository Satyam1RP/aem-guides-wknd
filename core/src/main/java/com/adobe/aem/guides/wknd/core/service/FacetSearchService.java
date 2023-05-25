package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;

import com.google.gson.JsonObject;

public interface FacetSearchService {
    public JsonObject searchresult(String[] searchTags, String searchpath, SlingHttpServletRequest request);
}
