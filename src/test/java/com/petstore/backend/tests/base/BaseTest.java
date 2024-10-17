package com.petstore.backend.tests.base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * BaseTest class for setting up the base URI for backend tests.
 */
public class BaseTest {
    // Base URI for API endpoints
    protected String baseURI = new String();

    /**
     * Sets up the base URI before each test method.
     *
     * @param baseURI The base URI for API requests.
     */
    @BeforeMethod
    @Parameters({"baseURI"})
    public void setup(String baseURI) {
        this.baseURI = baseURI;
    }
}
