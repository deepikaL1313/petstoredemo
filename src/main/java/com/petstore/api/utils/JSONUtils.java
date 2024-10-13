package com.petstore.api.utils;

import com.google.gson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.petstore.api.requestDTO.AddPetToStoreDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONUtils {
    private Logger log = LogManager.getLogger(this.getClass().getName());

    // Method to fetch a JSON array from a file in the classpath
    public JsonArray fetchJSONArray(String inputJsonFileName) {
        JsonArray jsonArray = null;
        try {
            // Open the file as an InputStream
            InputStream inputStream = new ClassPathResource(inputJsonFileName).getInputStream();

            // Parse the JSON content
            JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            jsonArray = jsonElement.getAsJsonArray();
        } catch (IOException e) {
            // Log any exceptions encountered during file reading
            log.error("Error reading JSON file", e);
            throw new RuntimeException(e);
        }
        return jsonArray;
    }
}