package com.adobe.aem.guides.wknd.core.helper;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = { SlingHttpServletRequest.class,
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductImagesDTO {
    
    @ValueMapValue
    private String image;

     @ValueMapValue
    private String altText;

    public String getAltText() {
        return altText;
    }

    public String getImage() {
        return image;
    }

    

}
