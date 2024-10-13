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



public class DataProviders {


    private Logger log = LogManager.getLogger(this.getClass().getName());
    JSONUtils jsonUtil = new JSONUtils();

    // Method to read test data through Json file

    @DataProvider(name = "testDataToAddPetToStore", parallel = false)
    public Object[] testDataToAddPetToStore() {

        // Fetch the JSON array from the given path
        JsonArray jsonArray = jsonUtil.fetchJSONArray(Constants.ADD_PET_TO_STORE_JSON);

        // Initialize Gson
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        Object jsonData = new Object();

        // Initialize a list to hold AddPetToStoreDTO objects
        List<AddPetToStoreDTO> dtoList = new ArrayList<>();

        // Iterate through the JsonArray, convert each element to AddPetToStoreDTO, and add to dtoList
        for(JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = (JsonObject) jsonParser.parse(String.valueOf(jsonElement));
            Map<String, Object> map = gson.fromJson(jsonObject, Map.class);
            jsonData = map.get("AddPetsToStore");
            Object obj = gson.fromJson(gson.toJson(jsonData), AddPetToStoreDTO.class);
            dtoList.add((AddPetToStoreDTO) obj);
        }
        // Convert the list to an array and return
        return dtoList.toArray();
    }

    // Method to read test data through Excel file
    @DataProvider(name = "testDataForUpdateOfPetNameStatus", parallel = false)
    public Object[][] testDataForUpdateOfPetNameStatus() {
        // Create an instance of ExcelUtility to fetch data from the Excel sheet
        String[][] data = new ExcelUtility()
                .getDataFromExcelSheet(Constants.PATH_TO_TEST_DATA_EXCEL_SHEET,
                        "TestDataPet",
                        "testDataForUpdateOfPetNameStatus");

        // Return the data as a two-dimensional array
        return data;
    }

}
