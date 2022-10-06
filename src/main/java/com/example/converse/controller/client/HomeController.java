package com.example.converse.controller.client;

import java.io.Console;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.converse.entity.Product;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.sercurity.CustomUserDetails;
import com.example.converse.service.ProductService;
import com.example.converse.service.UserService;
import com.example.converse.service.impl.UserServiceImpl;

@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getHome(Model model){
        List<Product> listProduct = productService.getListProduct();

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

}
