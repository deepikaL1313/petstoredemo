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

/**
 * PetsServiceController class for interacting with the Pet Store API.
 */

public class PetsServiceController {
    // Base URI for the Pet Store API
    String baseURI = new String();

    // Logger for logging information
    private Logger log = LogManager.getLogger(this.getClass().getName());

    // Initialize RestUtil class for making REST calls
    RestUtils restUtil = new RestUtils();

    /**
     * Constructor for PetsServiceController.
     *
     * @param baseURI The base URI for the Pet Store API.
     */
    public PetsServiceController(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Adds a pet to the store.
     *
     * @param addPetDataToStore The data of the pet to add.
     * @return The response from the API.
     */
    public Response addPetToStore(Object addPetDataToStore) {
        String basePath = "/pet";
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", Constants.Accept);
        headers.put("Content-Type", Constants.Content_Type);
        headers.put("X-API-KEY", Constants.Api_Key);

        // Build the request specification
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)                 // Set the base URI
                .basePath(basePath)               // Set the base path
                .method("POST")                   // Specify the HTTP method
                .headers(headers)                 // Add headers
                .log(LogDetail.ALL)               // Enable logging of all details
                .requestBody(addPetDataToStore)   // Set the request body with data to add the pet
                .build();

        // Execute the request and get the response
        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }

    /**
     * Retrieves a pet by its ID.
     *
     * @param petID The ID of the pet to retrieve.
     * @return The response from the API.
     */
    public Response getPetByID(String petID) {
        String basePath = "/pet/" + petID;
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", Constants.Accept);
        headers.put("accept", Constants.Content_Type);
        headers.put("X-API-KEY", Constants.Api_Key);

        // Build the request specification
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)        // Set the base URI
                .basePath(basePath)      // Set the base path
                .method("GET")           // Specify the HTTP method
                .headers(headers)        // Add headers
                .log(LogDetail.ALL)      // Enable logging of all details
                .build();
        // Execute the request and get the response
        Response response = restUtil.restCall(requestSpecificationDTO, null);
        return response;
    }

    /**
     * Finds pets by their status.
     *
     * @param status The status to filter pets by.
     * @return The response from the API.
     */
    public Response findPetByStatus(String status) {
        String basePath = "/pet/findByStatus";
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", Constants.Accept);
        headers.put("Content-Type", Constants.Content_Type);
        headers.put("X-API-KEY", Constants.Api_Key);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("status", status);

        // Build the request specification
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)                 // Set the base URI
                .basePath(basePath)               // Set the base path
                .method("DELETE")                 // Specify the HTTP method
                .headers(headers)                 // Add headers
                .queryParam(queryParams)          // Add query parameters
                .log(LogDetail.ALL)               // Enable logging of all details
                .build();

        // Execute the request and get the response
        Response response = restUtil.restCall(requestSpecificationDTO, null);

       // Return the API response
        return response;
    }

    /**
     * Deletes a pet by its ID.
     *
     * @param petID The ID of the pet to delete.
     * @return The response from the API.
     */
    public Response deletePet(String petID) {
        String basePath = "/pet/" + petID;
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", Constants.Accept);
        headers.put("Content-Type", Constants.Content_Type);
        headers.put("X-API-KEY", Constants.Api_Key);

        // Build the request specification
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)        // Set the base URI
                .basePath(basePath)      // Set the base path
                .method("DELETE")        // Specify the HTTP method
                .headers(headers)        // Add headers
                .log(LogDetail.ALL)      // Enable logging of all details
                .build();

        // Execute the request and get the response
        Response response = restUtil.restCall(requestSpecificationDTO, null);

       // Return the API response
        return response;
    }

    /**
     * Updates a pet's name and status by its ID.
     *
     * @param petID The ID of the pet to update.
     * @param updatedNameStatus A map containing the updated name and status.
     * @return The response from the API.
     */
    public Response updatePetNameAndStatusByPetID(String petID, Map<String, String> updatedNameStatus) {
        String basePath = "/pet/" + petID;
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", Constants.Accept);
        headers.put("Content-Type", Constants.Content_Type_Form_URlEncoded);
        headers.put("X-API-KEY", Constants.Api_Key);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", updatedNameStatus.get("Name"));
        formParams.put("status", updatedNameStatus.get("Status"));

        // Build the request specification
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("POST")
                .headers(headers)
                .formParam(formParams)
                .log(LogDetail.ALL)
                .build();

        // Execute the request and get the response
        Response response = restUtil.restCall(requestSpecificationDTO, null);

        // Return the API response
        return response;
    }

    /**
     * Uploads a pet picture by its ID.
     *
     * @param petID The ID of the pet to upload a picture for.
     * @return The response from the API.
     */
    public Response uploadPetPictureByID(String petID) {
        String basePath = "/pet/" + petID;
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", Constants.Accept);
        headers.put("Content-Type", Constants.Content_Type_MultiPart);
        headers.put("X-API-KEY", Constants.Api_Key);

        // Build the request specification
        RequestSpecificationDTO requestSpecificationDTO = RequestSpecificationDTO.builder()
                .baseUri(baseURI)
                .basePath(basePath)
                .method("POST")
                .headers(headers)
                .multiPart(new File(Constants.PATH_TO_PET_IMAGE))
                .log(LogDetail.ALL)
                .build();

        // Execute the request and get the response
        Response response = restUtil.restCall(requestSpecificationDTO, null);

        // Return the API response
        return response;
    }
}
