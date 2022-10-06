package com.example.converse.controller.client;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.converse.entity.Order;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.sercurity.CustomUserDetails;
import com.example.converse.service.OrderService;

@Controller
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String getOrderPage(){
        return "client/checkout";
    }

    @PostMapping("/client/api/save-order")
    public ResponseEntity<?> saveOrder(){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        orderService.saveOrder(user);

        return ResponseEntity.ok("Thành công");
    }
}
