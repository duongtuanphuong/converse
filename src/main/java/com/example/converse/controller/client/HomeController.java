package com.example.converse.controller.client;

import java.io.Console;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.converse.entity.Category;
import com.example.converse.entity.Product;
import com.example.converse.entity.User;
import com.example.converse.sercurity.CustomUserDetails;
import com.example.converse.service.CategoryService;
import com.example.converse.service.ProductService;


@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String getHome(Model model){

        List<Product> listProduct = productService.getListNewProduct();
        List<Category> listCategory = categoryService.getListCategory();
        List<Product> listProductByCategory = productService.getListProductByCategoryId(4);
        List<Product> listProductByCategory2 = productService.getListProductByCategoryId(2);
        List<Product> listProductByCost = productService.getListProductByCost();
        model.addAttribute("listProductByCategory2", listProductByCategory2);
        model.addAttribute("listProductCategory", listProductByCategory);
        model.addAttribute("listProductByCost", listProductByCost);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("listProduct", listProduct);
        return "client/index";
    }
    

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable long id,Model model){
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "client/product-detail";
    }


    @GetMapping("/test")
    public String getTest(Model model){
        return "index";
    }

    @GetMapping("/product/category/{id}")
    public String getListProductByCategoryId(@PathVariable long id,Model model,
                                            @RequestParam(defaultValue = "0")Integer pageNo,
                                            @RequestParam(defaultValue = "12")Integer pageSize,
                                            @RequestParam(defaultValue = "id")String sortBy){
        List<Product> newProducts = productService.getNewListProductByCategoryId(id);
        Page<Product> listProduct = productService.getListProductByCategoryId(id, pageNo, pageSize, sortBy);
        int totalPages = listProduct.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("newProductList", newProducts);
        return "client/list-product";
    }



}
