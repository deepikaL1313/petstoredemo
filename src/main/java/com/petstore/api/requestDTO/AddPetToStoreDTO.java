package com.petstore.api.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Data Transfer Object for adding a pet to the store.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPetToStoreDTO {
    public int id;                    // Pet ID
    public Category category;         // Pet category
    public String name;               // Pet name
    public ArrayList<String> photoUrls; // List of photo URLs
    public ArrayList<Tag> tags;       // List of tags
    public String status;             // Pet status (e.g., available, pending, sold)

    /**
     * Inner class representing a category.
     */
    @Data
    public class Category {
        public int id;                // Category ID
        public String name;           // Category name
    }

    /**
     * Inner class representing a tag.
     */
    @Data
    public class Tag {
        public int id;                // Tag ID
        public String name;           // Tag name
    }
}
