package com.petstore.api.requestDTO;

import io.restassured.filter.log.LogDetail;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.Map;

/**
 * Data Transfer Object for Request Specifications.
 */
@Data
@Builder(toBuilder = true)
public class RequestSpecificationDTO {
    private String baseUri;                 // Base URI for the API
    private String basePath;                // Base path for the API endpoint
    private String method;                  // HTTP method (GET, POST, etc.)
    private Map<String, String> headers;    // HTTP headers
    private Map<String, String> queryParam; // Query parameters
    private Map<String, String> formParam;  // Form parameters
    private Object requestBody;             // Request body
    private Object requestObject;           // Request object
    private String authUname;               // Authentication username
    private String authPassword;            // Authentication password
    private String proxy;                   // Proxy settings
    private Boolean urlEncoding;            // URL encoding flag
    private String contentType;             // Content type
    private LogDetail log;                  // Logging detail level
    private File multiPart;                 // Multipart file
}
