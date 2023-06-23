package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

import com.adobe.aem.guides.wknd.core.helper.ProductCategoryDTO;
import com.adobe.aem.guides.wknd.core.helper.ProductImagesDTO;

public interface Product {

    String getProductName();
    List<ProductCategoryDTO> getCategories();
    List<ProductImagesDTO> getImages();
    String getProductDescription();
    boolean isEmpty();
}
