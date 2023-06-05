package com.adobe.aem.guides.wknd.core.models;

import java.util.List;
import com.adobe.aem.guides.wknd.core.helper.UniversityCardCountsDTO;

public interface UniversityCard {
    String getTitle();
    String getImagePath();
    String getVideoPath();
    String getLinearGradient();
    String getVideoType();
    List<UniversityCardCountsDTO> getCounts();
    boolean isEmpty();

    
}
