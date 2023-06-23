package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.aem.guides.wknd.core.helper.ProductCategoryDTO;
import com.adobe.aem.guides.wknd.core.helper.ProductImagesDTO;
import com.adobe.aem.guides.wknd.core.models.Product;

@Model(adaptables = { SlingHttpServletRequest.class,Resource.class }, adapters = { Product.class }, resourceType = {
    ProductImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductImpl implements Product{
     private static final Logger LOG = LoggerFactory.getLogger(ProductImpl.class);
    protected static final String RESOURCE_TYPE = "wknd/components/product";

    @ValueMapValue
    private String productName;

    @ChildResource
    private List<ProductCategoryDTO> categories;

    @ChildResource
    List<ProductImagesDTO> images;

    @ValueMapValue
    private String productDescription;

    @Override
    public String getProductName() {
       return productName;
    }

    @Override
    public List<ProductCategoryDTO> getCategories() {
        return categories;
    }

    @Override
    public List<ProductImagesDTO> getImages() {
       return images;
    }

    @Override
    public String getProductDescription() {
       return productDescription;
    }

    @Override
    public boolean isEmpty() {
       return StringUtils.isBlank(productName);
    }
    
}
