package com.petstore.api.utils;


import com.google.gson.Gson;
import com.petstore.api.requestDTO.RequestSpecificationDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * This class has methods to build Request / Response Specifications and
 * perform Rest Calls of type GET, PUT, POST, PATCH and DELETE
 */

public class RestUtils {
    private Logger log = LogManager.getLogger(this.getClass().getName());

    public <T> T restCall(RequestSpecificationDTO requestSpecificationDTO, Class<T> clazz) {
        Response response = null;
        Gson gson = new Gson();
        try {
            RequestSpecification requestSpecification = requestSpecificationCreation(requestSpecificationDTO);
            requestSpecification.log().all();
            switch (requestSpecificationDTO.getMethod()) {
                case "GET":
                    response = requestSpecification.get();
                    break;
                case "POST":
                    response = requestSpecification.post();
                    break;
                case "PUT":
                    response = requestSpecification.put();
                    break;
                case "DELETE":
                    response = requestSpecification.delete();
                    break;
                case "PATCH":
                    response = requestSpecification.patch();
                    break;
            }
            log.info("Response :"+response.getBody().asString());
            System.out.println( "Response :"+response.getBody().prettyPrint());
            if (null != clazz)
                return gson.fromJson(response.getBody().asString(), clazz);
            else
                return (T) response;
        } catch (Exception e) {
            log.error("Error occurred while executing restCall " + e.getMessage());
            return null;
        }
    }

    public RequestSpecification requestSpecificationCreation(RequestSpecificationDTO requestSpecDTO) {
        RequestSpecification requestSpecification = RestAssured.given();

        if(requestSpecDTO.getHeaders()!=null){
            requestSpecification.headers(requestSpecDTO.getHeaders());
        }
        if(requestSpecDTO.getQueryParam()!=null){
            requestSpecification.queryParams(requestSpecDTO.getQueryParam());
        }
        if(requestSpecDTO.getFormParam()!=null){
            requestSpecification.formParams(requestSpecDTO.getFormParam());
        }
        if(requestSpecDTO.getContentType()!=null){
            requestSpecification.contentType(requestSpecDTO.getContentType());
        }
        if(requestSpecDTO.getBaseUri()!=null){
            requestSpecification.baseUri(requestSpecDTO.getBaseUri());
        }
        if(requestSpecDTO.getBasePath()!=null){
            requestSpecification.basePath(requestSpecDTO.getBasePath());
        }
        if(requestSpecDTO.getRequestBody()!=null){
            requestSpecification.body(requestSpecDTO.getRequestBody());
        }
        if(requestSpecDTO.getUrlEncoding()!=null){
            requestSpecification.urlEncodingEnabled(requestSpecDTO.getUrlEncoding());
        }
        if(requestSpecDTO.getMultiPart()!=null) {
            requestSpecification.multiPart(requestSpecDTO.getMultiPart());
        }
        if(requestSpecDTO.getAuthUname()!=null && requestSpecDTO.getAuthPassword()!=null) {
            requestSpecification.auth().basic(requestSpecDTO.getAuthUname(), requestSpecDTO.getAuthPassword());
        }

        requestSpecification.relaxedHTTPSValidation();
        return requestSpecification;
    }
}
