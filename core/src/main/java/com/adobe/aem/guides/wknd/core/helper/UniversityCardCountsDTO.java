package com.adobe.aem.guides.wknd.core.helper;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class,
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UniversityCardCountsDTO {

    @ValueMapValue 
    private String usePercentage;
    @ValueMapValue 
    private String minNumber;
    @ValueMapValue 
    private String maxNumber;
    @ValueMapValue 
    private String text;

    public String getUsePercentage() {
        return usePercentage;
    }
   
    public String getMinNumber() {
        return minNumber;
    }
   
    public String getMaxNumber() {
        return maxNumber;
    }
    
    public String getText() {
        return text;
    } 
}
