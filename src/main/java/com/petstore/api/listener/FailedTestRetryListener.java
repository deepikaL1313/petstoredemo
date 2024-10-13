package com.petstore.api.listener;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class FailedTestRetryListener implements IAnnotationTransformer {
    /*
    ** Overriding the transform method to set the RetryAnalyzer
     */
    @Override
    public void transform(ITestAnnotation testAnnotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        Class retry = testAnnotation.getRetryAnalyzerClass();
        if (retry !=RetryAnalyzer.class)
            testAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
