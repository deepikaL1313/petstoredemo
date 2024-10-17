package com.petstore.api.listener;

import com.petstore.api.constants.Constants;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    /**
     * Tracks the number of retry attempts.
     */
    int retryAttemptsCounter = 0;

    /**
     * Maximum retry attempts allowed.
     */
    int maxRetryLimit = Constants.MAX_RETRY_ATTEMPT;

    /**
     * Retries a failed test up to a maximum retry limit.
     *
     * @param result The result of the test execution.
     * @return true if the test should be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if(retryAttemptsCounter < maxRetryLimit){
                retryAttemptsCounter++;
                return true;
            }
        }
        return false;
    }
}