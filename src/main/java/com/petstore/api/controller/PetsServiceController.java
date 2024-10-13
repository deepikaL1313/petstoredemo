package com.petstore.api.controller;

import com.petstore.api.constants.Constants;
import com.petstore.api.requestDTO.RequestSpecificationDTO;
import com.petstore.api.utils.RestUtils;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PetsServiceController {
    String baseURI = new String();
    private Logger log = LogManager.getLogger(this.getClass().getName());
    RestUtils restUtil = new RestUtils();

    public PetsServiceController(String baseURI) {
        this.baseURI = baseURI;
    }

    public Response addPetToStore(Object addPetDataToStore)  {
        String basePath = "/pet";
        Map<String,String> headers = new HashMap<>();
        headers.put("accept",Constants.Accept);
        headers.put("Content-Type",Constants.Content_Type);
        headers.put("X-API-KEY",Constants.Api_Key);
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("POST")
                .headers(headers)
                .log(LogDetail.ALL)
                .requestBody(addPetDataToStore)
                .build();

        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }

    public Response getPetByID(String petID)  {
        String basePath = "/pet/"+petID;
        Map<String,String> headers = new HashMap<>();
        headers.put("accept",Constants.Accept);
        headers.put("accept",Constants.Content_Type);
        headers.put("X-API-KEY",Constants.Api_Key);
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("GET")
                .headers(headers)
                .log(LogDetail.ALL)
                .build();

        Response response = restUtil.restCall(requestSpecificationDTO, null);
     return response;
    }

    public Response findPetByStatus(String status)  {
        String basePath = "/pet/findByStatus";
        Map<String,String> headers = new HashMap<>();
        headers.put("accept",Constants.Accept);
        headers.put("Content-Type",Constants.Content_Type);
        headers.put("X-API-KEY",Constants.Api_Key);
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("status", status);
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("DELETE")
                .headers(headers)
                .queryParam(queryParams)
                .log(LogDetail.ALL)
                .build();
        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }
    public Response deletePet(String petID)  {
        String basePath = "/pet/"+petID;
        Map<String,String> headers = new HashMap<>();
        headers.put("accept",Constants.Accept);
        headers.put("Content-Type",Constants.Content_Type);
        headers.put("X-API-KEY",Constants.Api_Key);
        Map<String,String> queryParams = new HashMap<>();
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("DELETE")
                .headers(headers)
                .log(LogDetail.ALL)
                .build();
        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }

    public Response updatePetNameAndStatusByPetID(String petID, Map<String,String> updatedNameStatus)  {
        String basePath = "/pet/"+petID;
        Map<String,String> headers = new HashMap<>();
        headers.put("accept",Constants.Accept);
        headers.put("Content-Type",Constants.Content_Type_Form_URlEncoded);
        headers.put("X-API-KEY",Constants.Api_Key);
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", updatedNameStatus.get("Name"));
        formParams.put("status", updatedNameStatus.get("Status"));
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("POST")
                .headers(headers)
                .formParam(formParams)
                .log(LogDetail.ALL)
                .build();
        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }

    public Response uploadPetPictureByID(String petID, Map<String,String> updatedNameStatus)  {
        String basePath = "/pet/"+petID;
        Map<String,String> headers = new HashMap<>();
        headers.put("accept",Constants.Accept);
        headers.put("Content-Type",Constants.Content_Type_MultiPart);
        headers.put("X-API-KEY",Constants.Api_Key);
        Map<String,String> formParams = new HashMap<>();
        formParams.put("name", updatedNameStatus.get("Name"));
        formParams.put("status", updatedNameStatus.get("Status"));
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("POST")
                .headers(headers)
                .multiPart(new File(Constants.PATH_TO_PET_IMAGE))
                .log(LogDetail.ALL)
                .build();
        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }
}
