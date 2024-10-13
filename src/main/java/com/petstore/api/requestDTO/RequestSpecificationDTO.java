package com.petstore.api.requestDTO;

import io.restassured.filter.log.LogDetail;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class RequestSpecificationDTO {
    private String baseUri;
    private String basePath;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> queryParam;
    private Map<String, String> formParam;
    private Object requestBody;
    private Object requestObject;
    private String authUname;
    private String authPassword;
    private String proxy;
    private Boolean urlEncoding;
    private String contentType;
    private LogDetail log;
    private File multiPart;
}
