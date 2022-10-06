package com.example.converse.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.converse.entity.Category;
import com.example.converse.exception.InternalServerException;
import com.example.converse.exception.NotFoundException;
import com.example.converse.payload.request.CreateCategoryReq;
import com.example.converse.repository.CategoryRepository;
import com.example.converse.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getListCategory() {
        // TODO Auto-generated method stub
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryByID(long id) {
        // TODO Auto-generated method stub
        Category category  = categoryRepository.findById(id).get();
        return category;
    }

    @Override
    public Category createCategory(CreateCategoryReq req) {
        // TODO Auto-generated method stub
        Category category = new Category();
        category.setName(req.getName());
        category.set_deleted(false);
        return categoryRepository.save(category);
    }

    @Override
    public void updateCategory(long id, CreateCategoryReq req) {
        // TODO Auto-generated method stub
        Optional<Category> rs = categoryRepository.findById(id);
        if(rs.isEmpty()){
            throw new NotFoundException("Category không tồn tại!");
        }
        Category category = rs.get();
        category.setName(req.getName());
        try{
            categoryRepository.save(category);
        }catch(Exception e){
            throw new InternalServerException("Lỗi khi chỉnh sửa category");
        }

        
    }

    @Override
    public void enabledCategory(long id) {
        // TODO Auto-generated method stub
        Optional<Category> rs = categoryRepository.findById(id);
        if(rs.isEmpty()){
            throw new NotFoundException("Category không tồn tại!");
        }
        Category category = rs.get();
        if(category.is_deleted() == false){
            category.set_deleted(true);
        }
        else if(category.is_deleted() == true){
            category.set_deleted(false);
        }
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getListEnabled() {
        // TODO Auto-generated method stub
        return categoryRepository.findAllByEnabled();
    }

    @Override
    public void deleteCategory(long id) {
        // TODO Auto-generated method stub
        Optional<Category> rs = categoryRepository.findById(id);

        categoryRepository.delete(rs.get());
        
    }

    
    
}
