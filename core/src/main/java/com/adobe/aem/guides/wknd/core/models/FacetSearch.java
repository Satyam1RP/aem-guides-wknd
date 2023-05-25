package com.adobe.aem.guides.wknd.core.models;

import java.util.List;
import java.util.Map;

public interface FacetSearch {
    public String getHeaderText();
    public String getRootPath();
    public List<Map<String,String>> getIndustryList();
    public List<Map<String,String>> getSectorList();
    public List<Map<String,String>> getCountryList();
    boolean isEmpty();
}
