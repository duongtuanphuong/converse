package com.example.converse.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.converse.entity.Category;
import com.example.converse.payload.request.CreateCategoryReq;
import com.example.converse.service.CategoryService;

@Controller
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public String getListCategory(Model model){
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);
        return "admin/category";
    }
    @GetMapping("/api/category/enabled")
    public ResponseEntity<?> getListCategoryEnabled(){
        List<Category> categories = categoryService.getListEnabled();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/api/category/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryReq req){
        Category category = categoryService.createCategory(req);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/api/category/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @RequestBody CreateCategoryReq req){
        categoryService.updateCategory(id, req);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PostMapping("/api/category/enable/{id}")
    public ResponseEntity<?> enabledCategory(@PathVariable long id){
        categoryService.enabledCategory(id);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/category/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Xóa thành công");
    }

}
