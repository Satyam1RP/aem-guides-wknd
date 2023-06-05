package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.aem.guides.wknd.core.helper.UniversityCardCountsDTO;
import com.adobe.aem.guides.wknd.core.models.UniversityCard;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { UniversityCard.class }, resourceType = {
    UniversityCardImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UniversityCardImpl implements UniversityCard {
    private static final Logger LOG = LoggerFactory.getLogger(UniversityCardImpl.class);
    protected static final String RESOURCE_TYPE = "wknd/components/universitycard";
    protected static final String METADATA = "/jcr:content/metadata";
    protected static final String VIDEO_TYPE = "dc:format";

    @SlingObject
    ResourceResolver resourceResolver;
    
    @ChildResource
    private List<UniversityCardCountsDTO> counts;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String videoPath;

    @ValueMapValue
    private String bgImage;

    @ValueMapValue
    private String linearGradient;

    @Override
    public List<UniversityCardCountsDTO> getCounts() {
        return counts;
    }

    @Override
    public String getTitle() {
       return title;
    }
    @Override
    public String getImagePath() {
        return bgImage;
    }
    @Override
    public String getVideoPath() {
         return videoPath;
    }
    
    @Override
    public String getLinearGradient() {
       return linearGradient;
    }
    @Override
    public String getVideoType() {
       return resourceResolver.getResource(videoPath+METADATA).getValueMap().get(VIDEO_TYPE, String.class);
    }
   
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(title)&&StringUtils.isBlank(videoPath)&&StringUtils.isBlank(bgImage);
    }
}
