package com.example.converse.payload.request;

import java.util.ArrayList;

import com.example.converse.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductReq {
    private String name;

    private String description;

    private long price;

    private int quantity;

    private long category_id;

    @JsonProperty("image_ids")
    private ArrayList<Long> imageIds;
}
