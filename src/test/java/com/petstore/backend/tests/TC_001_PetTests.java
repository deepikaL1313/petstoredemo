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

public class TC_001_PetTests extends BaseTest {
    private Logger log = LogManager.getLogger(this.getClass().getName());

    @Test (groups = {"SANITY","REGRESSION"}, description = "validateAddNewPetToStore - Validates if a new Pet can be added to Store",
            dataProvider = "testDataToAddPetToStore", dataProviderClass = DataProviders.class)
    public void validateAddPetsToStore(AddPetToStoreDTO petData) {
        SoftAssert s_assert = new SoftAssert();
        boolean result = false;
        try {
            PetsServiceController p = new PetsServiceController(baseURI);
            Response response = p.addPetToStore(petData);
            response.getBody().prettyPrint();
            result= response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200 , it is found to be ["+response.getStatusCode()+"]");
            response = p.getPetByID(String.valueOf(petData.getId()));
            result = response.jsonPath().get("status").toString().equalsIgnoreCase(petData.getStatus());
            s_assert.assertTrue(result,"Status for the Pet did not match");

        } catch (Exception e) {
            s_assert.fail("Exception Occurred during execution of Test : ["+e.getLocalizedMessage()+"]");
            log.error("Exception Occurred during execution of Test ["+e.getMessage()+"]");
        }
        finally {
            s_assert.assertAll();
        }
    }

    @Test(groups = {"SANITY","REGRESSION"}, description = "validateUpdationOfPetNameAndStatus - Validate updating of name and status of a pet is successful",
            dataProvider = "testDataForUpdateOfPetNameStatus", dataProviderClass = DataProviders.class)
    public void validateUpdateOfPetNameAndStatus(String petID, String petName, String petStatus) {
        SoftAssert s_assert = new SoftAssert();
        boolean result = false;
        Response response = null;
        try {
            Map<String, String> nameStatus = new HashMap<>();
            nameStatus.put("Name",petName);
            nameStatus.put("Status",petStatus);
            PetsServiceController p = new PetsServiceController(baseURI);
            response = p.updatePetNameAndStatusByPetID(petID, nameStatus);
            response.getBody().prettyPrint();
            result= response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200 , it is found to be ["+response.getStatusCode()+"]");
            response = p.getPetByID(petID);
            response.getBody().prettyPrint();
            result= response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200 , it is found to be ["+response.getStatusCode()+"]");
            s_assert.assertEquals(response.jsonPath().get("status").toString(), petStatus,"Pet Status for pet ID ["+petID+"] did not match.");
             result = response.jsonPath().get("name").toString().equalsIgnoreCase(petName);
            s_assert.assertEquals(response.jsonPath().get("name").toString(), petStatus,"Pet Name for pet ID ["+petID+"] did not match.");
        } catch (Exception e) {
            s_assert.fail("Exception Occurred during execution of Test : ["+e.getLocalizedMessage()+"]");
            log.error("Exception Occurred during execution of Test ["+e.getMessage()+"]");
        }
        finally {
            s_assert.assertAll();
        }
    }

    @Test(groups = {"SANITY","REGRESSION"}, description = "validateUpdationOfPetNameAndStatus - Validate updating of name and status of a pet is successful",
            dataProvider = "testDataForRemovalOfPetFromStore", dataProviderClass = DataProviders.class)
    public void validateRemovalOfPetFromStore(String petID) {
        SoftAssert s_assert = new SoftAssert();
        boolean result = false;
        Response response = null;
        try {
            PetsServiceController p = new PetsServiceController(baseURI);
            response = p.deletePet(petID);
            response.getBody().prettyPrint();
            result= response.getStatusCode() == 200;
            s_assert.assertTrue(result, "Response code is not 200 , it is found to be ["+response.getStatusCode()+"]");
            response = p.getPetByID(petID);
            response.getBody().prettyPrint();
            result= response.getStatusCode() == 404;
            s_assert.assertTrue(result, "Response code is not 404 , it is found to be ["+response.getStatusCode()+"]");
        } catch (Exception e) {
            s_assert.fail("Exception Occurred during execution of Test : ["+e.getLocalizedMessage()+"]");
            log.error("Exception Occurred during execution of Test ["+e.getMessage()+"]");
        }
        finally {
            s_assert.assertAll();
        }
    }

}
