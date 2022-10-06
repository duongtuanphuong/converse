package com.example.converse.service;

import java.util.List;

import com.example.converse.entity.Category;
import com.example.converse.payload.request.CreateCategoryReq;

public interface CategoryService {
    
    List<Category> getListCategory();

    List<Category> getListEnabled();
    
    Category findCategoryByID(long id);

    Category createCategory(CreateCategoryReq req);

    void updateCategory(long id, CreateCategoryReq req);

    void deleteCategory(long id);
    
    void enabledCategory(long id);  
}
