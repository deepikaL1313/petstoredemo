package com.petstore.backend.tests;

import com.petstore.api.controller.PetsServiceController;
import com.petstore.api.requestDTO.AddPetToStoreDTO;
import com.petstore.backend.tests.base.BaseTest;
import com.petstore.backend.tests.data.DataProviders;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

/**
 * Test class for Pet Store API test cases.
 */
public class TC_001_PetTests extends BaseTest {
    // Logger for logging information
    private Logger log = LogManager.getLogger(this.getClass().getName());

    /**
     * Validates if a new Pet can be added to the store.
     *
     * @param petData Data of the pet to be added.
     */
    @Test(priority = 1, groups = {"SANITY", "REGRESSION"}, description = "validateAddNewPetToStore - Validates if a new Pet can be added to Store",
            dataProvider = "testDataToAddPetToStore", dataProviderClass = DataProviders.class)
    public void validateAddPetsToStore(AddPetToStoreDTO petData) {
        SoftAssert s_assert = new SoftAssert();
        boolean result = false;
        try {
            PetsServiceController p = new PetsServiceController(baseURI);
            log.info("Add a new pet to store");
            Response response = p.addPetToStore(petData);
            response.getBody().prettyPrint();
            log.info("Verify pet addition to store is successful");
            result = response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200, it is found to be [" + response.getStatusCode() + "] : Pet addtion to Store failed");

            log.info("Fetch pet by ID");
            response = p.getPetByID(String.valueOf(petData.getId()));

            log.info("Verify pet is fetched from the store");
            result = response.getStatusCode() == 200;

            log.info("Validate Pet status is same as the created status");
            result = response.jsonPath().get("status").toString().equalsIgnoreCase(petData.getStatus());
            s_assert.assertTrue(result, "Status for the Pet did not match");

        } catch (Exception e) {
            s_assert.fail("Exception Occurred during execution of Test: [" + e.getLocalizedMessage() + "]");
            log.error("Exception Occurred during execution of Test [" + e.getMessage() + "]");
        } finally {
            s_assert.assertAll();
        }
    }

    /**
     * Validates updating of name and status of a pet.
     *
     * @param petID     The ID of the pet to update.
     * @param petName   The new name of the pet.
     * @param petStatus The new status of the pet.
     */
    @Test(priority = 2, groups = {"SANITY", "REGRESSION"}, description = "validateUpdateOfPetNameAndStatus - Validate updating of name and status of a pet is successful",
            dataProvider = "testDataForUpdateOfPetNameStatus", dataProviderClass = DataProviders.class)
    public void validateUpdateOfPetNameAndStatus(String petID, String petName, String petStatus) {
        SoftAssert s_assert = new SoftAssert();
        boolean result = false;
        Response response = null;
        try {
            Map<String, String> nameStatus = new HashMap<>();
            nameStatus.put("Name", petName);
            nameStatus.put("Status", petStatus);
            PetsServiceController p = new PetsServiceController(baseURI);

            log.info(" Update name and status of a pet identified by Pet ID ["+petID+"]");
            response = p.updatePetNameAndStatusByPetID(petID, nameStatus);
            response.getBody().prettyPrint();
            result = response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200, it is found to be [" + response.getStatusCode() + "]");
            log.info(" Verify Update is successful - Response code : ["+response.getStatusCode() +"]");

            log.info("Check Update is successful by fetching the Pet by ID");
            response = p.getPetByID(petID);
            response.getBody().prettyPrint();
            result = response.getStatusCode() == 200;
            log.info("Verify Get Pet by ID - Response : ["+ response.getStatusCode() +"]" );
            s_assert.assertTrue(result, "Response code is not 200, it is found to be [" + response.getStatusCode() + "]");

            s_assert.assertEquals(response.jsonPath().get("status").toString(), petStatus, "Pet Status for pet ID [" + petID + "] did not match.");
            log.info("Expected ["+response.jsonPath().get("status").toString()+"] , Actual ["+ petStatus+"]");

            s_assert.assertEquals(response.jsonPath().get("name").toString(), petName, "Pet Name for pet ID [" + petID + "] did not match.");
            log.info("Expected ["+response.jsonPath().get("name").toString()+"] , Actual ["+ petName+"]");
        } catch (Exception e) {
            s_assert.fail("Exception Occurred during execution of Test: [" + e.getLocalizedMessage() + "]");
            log.error("Exception Occurred during execution of Test [" + e.getMessage() + "]");
        } finally {
            s_assert.assertAll();
        }
    }

    /**
     * Validates removal of a pet from the store.
     *
     * @param petID The ID of the pet to remove.
     */
    @Test(priority = 3 , groups = {"SANITY", "REGRESSION"}, description = "validateRemovalOfPetFromStore - Validate removal of a pet from the store",
            dataProvider = "testDataForRemovalOfPetFromStore", dataProviderClass = DataProviders.class)
    public void validateRemovalOfPetFromStore(String petID) {
        SoftAssert s_assert = new SoftAssert();
        boolean result = false;
        Response response = null;
        try {
            PetsServiceController p = new PetsServiceController(baseURI);

            log.info(" Delete Pet identified by Pet ID ["+petID+"]");
            response = p.deletePet(petID);
            response.getBody().prettyPrint();
            result = response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200, it is found to be [" + response.getStatusCode() + "] - Delete was not succusessful");

            log.info("Fetch pet by ID");
            response = p.getPetByID(petID);
            response.getBody().prettyPrint();
            result = response.getStatusCode() == 404;
            log.info("Verify Pet is not found ["+response.getStatusCode() +"]");
            s_assert.assertTrue(result, "Response code is not 404, it is found to be [" + response.getStatusCode() + "] : Pet is not deleted");
        } catch (Exception e) {
            s_assert.fail("Exception Occurred during execution of Test: [" + e.getLocalizedMessage() + "]");
            log.error("Exception Occurred during execution of Test [" + e.getMessage() + "]");
        } finally {
            s_assert.assertAll();
        }
    }
}
