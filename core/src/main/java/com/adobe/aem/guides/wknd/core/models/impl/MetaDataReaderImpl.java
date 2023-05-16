package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.resource.Resource;

import com.adobe.aem.guides.wknd.core.models.MetaDataReader;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { MetaDataReader.class }, resourceType = {
    MetaDataReaderImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class MetaDataReaderImpl implements MetaDataReader {
   
    private static final Logger LOG = LoggerFactory.getLogger(MetaDataReaderImpl.class);
    protected static final String RESOURCE_TYPE = "wknd/components/metadatareader";

    String METADATA = "/jcr:content/metadata";
    String MIME_TYPE = "dam:MIMEtype";
    String TITLE = "dc:title";
    String PNG_DESCRIPTION = "dc:imagediscription";
    String PNG_TITLE = "dc:imagetitle"; 


    @SlingObject
    ResourceResolver resourceResolver;

    @ValueMapValue
    private String assetPath;

    List<Map<String, String>> metaDataList = new ArrayList<>();

    @PostConstruct
    public void init(){
        Resource res = resourceResolver.getResource(assetPath);
        if(res!=null && res.hasChildren()){
            Iterator<Resource> children = res.listChildren();
            while(children.hasNext()){
                Map<String, String> metaDataMap = new HashMap<>();
                Resource child = children.next();
                String rootPath = child.getPath();
                String nodeName = child.getName();
                if(!"jcr:content".equals(nodeName)){
                    ValueMap metaDataProperties = resourceResolver.getResource(rootPath+METADATA).getValueMap();
                    String imageType = metaDataProperties.get(MIME_TYPE, String.class);
                    if(imageType.equals("image/png")){
                        metaDataMap.put("imagePath",rootPath);
                        metaDataMap.put("mimeType", imageType);
                        metaDataMap.put("title", metaDataProperties.get(TITLE, String.class));
                        metaDataMap.put("pngTitle", metaDataProperties.get(PNG_TITLE, String.class));
                        metaDataMap.put("pngDescription", metaDataProperties.get(PNG_DESCRIPTION, String.class));
                        metaDataList.add(metaDataMap);

                    }
                }      
            }
        }
    }
    @Override
    public List<Map<String, String>> getAssetData() {
        return metaDataList;      
    }
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(assetPath);
    }
}
