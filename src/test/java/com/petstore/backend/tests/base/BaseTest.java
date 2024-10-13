package com.petstore.backend.tests.base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected String baseURI = new String();

    @BeforeMethod
    @Parameters({"baseURI"})
    public void setup(String baseURI) {
        this.baseURI = baseURI;
    }
}
