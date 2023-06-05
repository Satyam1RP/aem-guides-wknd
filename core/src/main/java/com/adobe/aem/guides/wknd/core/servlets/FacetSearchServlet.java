package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.service.FacetSearchService;
import com.google.gson.JsonObject;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Query Builder servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/facetsearch" })
public class FacetSearchServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(FacetSearchServlet.class); 
    @Reference
    FacetSearchService facetSearchService;    
    @Override
    protected void doGet( SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
                LOG.info("Inside doget");
                JsonObject searchResult = null;
                 try {
                       String stags = request.getParameter("searchTags");
                       String searchPath = request.getParameter("searchPath");
                       String[] searchTags = stags.split(",");
                       searchResult = facetSearchService.searchresult(searchTags, searchPath, request);
                      } catch (Exception e) {
                                      LOG.info("\n ERROR {} ", e.getMessage());
                        }
                response.getWriter().write(searchResult.toString());
    } 
    
}
