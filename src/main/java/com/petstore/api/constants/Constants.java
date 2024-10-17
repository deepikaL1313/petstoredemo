package com.petstore.api.constants;

public class Constants {

    public static final int MAX_RETRY_ATTEMPT = 1;
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String ADD_PET_TO_STORE_JSON = "AddPetToStore.json";
    public static final String PATH_TO_TEST_DATA_EXCEL_SHEET = PROJECT_PATH + "/src/test/java/com/petstore/backend/tests/data/TestDataForPetStoreDemo.xls";
    public static final String Accept = "application/json";
    public static final String Content_Type = "application/json";
    public static final String Content_Type_Form_URlEncoded = "application/x-www-form-urlencoded";
    public static final String Content_Type_MultiPart = "multipart/form-data";
    public static final String Api_Key = "special-key";
    public static final String PATH_TO_PET_IMAGE = "images/pet.png";

}
