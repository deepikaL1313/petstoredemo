package com.petstore.backend.tests.data;

import com.google.gson.*;
import com.petstore.api.constants.Constants;
import com.petstore.api.requestDTO.AddPetToStoreDTO;
import com.petstore.api.utils.ExcelUtility;
import com.petstore.api.utils.JSONUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DataProviders class for supplying test data to test methods.
 */
public class DataProviders {
    // Logger for logging information
    private Logger log = LogManager.getLogger(this.getClass().getName());

    // JSON utility instance
    JSONUtils jsonUtil = new JSONUtils();

    /**
     * Provides test data to add a pet to the store from a JSON file.
     *
     * @return An array of AddPetToStoreDTO objects.
     */
    @DataProvider(name = "testDataToAddPetToStore", parallel = false)
    public Object[] testDataToAddPetToStore() {
        // Fetch the JSON array from the given path
        JsonArray jsonArray = jsonUtil.fetchJSONArray(Constants.ADD_PET_TO_STORE_JSON);

        // Initialize Gson for JSON operations
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        Object jsonData;

        // Initialize a list to hold AddPetToStoreDTO objects
        List<AddPetToStoreDTO> dtoList = new ArrayList<>();

        // Iterate through the JsonArray
        for (JsonElement jsonElement : jsonArray) {
            // Parse each JSON element to a JsonObject
            JsonObject jsonObject = (JsonObject) jsonParser.parse(String.valueOf(jsonElement));

            // Convert the JsonObject to a Map
            Map<String, Object> map = gson.fromJson(jsonObject, Map.class);

            // Extract the "AddPetsToStore" key data
            jsonData = map.get("AddPetsToStore");

            // Convert the extracted data to AddPetToStoreDTO and add to the list
            AddPetToStoreDTO dto = gson.fromJson(gson.toJson(jsonData), AddPetToStoreDTO.class);
            dtoList.add(dto);
        }

        // Convert the list to an array and return
        return dtoList.toArray();
    }

    /**
     * Provides test data to update the name and status of a pet from an Excel file.
     *
     * @return A two-dimensional array of test data.
     */
    @DataProvider(name = "testDataForUpdateOfPetNameStatus", parallel = false)
    public Object[][] testDataForUpdateOfPetNameStatus() {
        // Fetch data from the Excel sheet using ExcelUtility
        String[][] data = new ExcelUtility()
                .getDataFromExcelSheet(Constants.PATH_TO_TEST_DATA_EXCEL_SHEET,
                        "TestDataPet",
                        "testDataForUpdateOfPetNameStatus");

        // Return the fetched data as a two-dimensional array
        return data;
    }
}
