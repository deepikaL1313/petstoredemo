package com.petstore.api.utils;

import com.google.gson.Gson;
import com.petstore.api.requestDTO.RequestSpecificationDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class has methods to build Request / Response Specifications and
 * perform REST calls of type GET, PUT, POST, PATCH, and DELETE.
 */
public class RestUtils {
    // Logger for logging information
    private Logger log = LogManager.getLogger(this.getClass().getName());

    /**
     * Performs a REST call based on the given request specification.
     *
     * @param requestSpecificationDTO The request specification.
     * @param clazz The class type of the response.
     * @return The response from the API.
     */
    public <T> T restCall(RequestSpecificationDTO requestSpecificationDTO, Class<T> clazz) {
        Response response = null;
        Gson gson = new Gson();
        try {
            // Create the request specification
            RequestSpecification requestSpecification = requestSpecificationCreation(requestSpecificationDTO);
            requestSpecification.log().all();

            // Perform the appropriate REST call based on the HTTP method
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
            log.info("Response :" + response.getBody().asString());
            System.out.println("Response :" + response.getBody().prettyPrint());

            // Convert the response to the specified class type if provided
            if (null != clazz)
                return gson.fromJson(response.getBody().asString(), clazz);
            else
                return (T) response;
        } catch (Exception e) {
            log.error("Error occurred while executing restCall " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a RequestSpecification object based on the given DTO.
     *
     * @param requestSpecDTO The request specification DTO.
     * @return The created RequestSpecification object.
     */
    public RequestSpecification requestSpecificationCreation(RequestSpecificationDTO requestSpecDTO) {
        RequestSpecification requestSpecification = RestAssured.given();

        // Add headers if present
        if (requestSpecDTO.getHeaders() != null) {
            requestSpecification.headers(requestSpecDTO.getHeaders());
        }

        // Add query parameters if present
        if (requestSpecDTO.getQueryParam() != null) {
            requestSpecification.queryParams(requestSpecDTO.getQueryParam());
        }

        // Add form parameters if present
        if (requestSpecDTO.getFormParam() != null) {
            requestSpecification.formParams(requestSpecDTO.getFormParam());
        }

        // Set content type if present
        if (requestSpecDTO.getContentType() != null) {
            requestSpecification.contentType(requestSpecDTO.getContentType());
        }

        // Set base URI if present
        if (requestSpecDTO.getBaseUri() != null) {
            requestSpecification.baseUri(requestSpecDTO.getBaseUri());
        }

        // Set base path if present
        if (requestSpecDTO.getBasePath() != null) {
            requestSpecification.basePath(requestSpecDTO.getBasePath());
        }

        // Add request body if present
        if (requestSpecDTO.getRequestBody() != null) {
            requestSpecification.body(requestSpecDTO.getRequestBody());
        }

        // Set URL encoding if present
        if (requestSpecDTO.getUrlEncoding() != null) {
            requestSpecification.urlEncodingEnabled(requestSpecDTO.getUrlEncoding());
        }

        // Add multi-part file if present
        if (requestSpecDTO.getMultiPart() != null) {
            requestSpecification.multiPart(requestSpecDTO.getMultiPart());
        }

        // Set basic authentication if present
        if (requestSpecDTO.getAuthUname() != null && requestSpecDTO.getAuthPassword() != null) {
            requestSpecification.auth().basic(requestSpecDTO.getAuthUname(), requestSpecDTO.getAuthPassword());
        }

        // Enable relaxed HTTPS validation
        requestSpecification.relaxedHTTPSValidation();

        return requestSpecification;
    }
}
