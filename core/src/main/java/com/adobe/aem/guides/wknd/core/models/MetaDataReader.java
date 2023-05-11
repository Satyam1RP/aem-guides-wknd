package com.adobe.aem.guides.wknd.core.models;

import java.util.List;
import java.util.Map;

public interface MetaDataReader {
    List<Map<String,String>> getAssetData();
    boolean isEmpty();
}
