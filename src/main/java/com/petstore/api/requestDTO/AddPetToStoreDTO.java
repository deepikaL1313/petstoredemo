package com.petstore.api.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPetToStoreDTO {

    public int id;
    public Category category;
    public String name;
    public ArrayList<String> photoUrls;
    public ArrayList<Tag> tags;
    public String status;
    @Data
    public class Category{
        public int id;
        public String name;
    }
   @Data
    public class Tag{
        public int id;
        public String name;
    }

}
