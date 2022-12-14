package com.example.converse.controller.client;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.Product;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.AddProductToCartReq;
import com.example.converse.sercurity.CustomUserDetails;
import com.example.converse.service.ProductService;
import com.example.converse.service.ShoppingCartService;
import com.example.converse.service.UserService;

@Controller
public class ShoppingCartController {
    
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String getCart(Model model,Principal principal){

        if(principal != null){
            User user = userService.getUser(principal.getName());
            ShoppingCart cart = user.getShoppingCart();
            if(cart != null){
                List<CartItem> cartItems = shoppingCartService.getListCartItem(cart.getId());
                model.addAttribute("cartItems", cartItems);
                model.addAttribute("cart",cart);
            }
        }
        return "client/cart";
    }


    @PostMapping("/client/api/add-to-cart")
    public ResponseEntity<?> addItemToCart(@RequestBody AddProductToCartReq req){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if(user == null){
            return ResponseEntity.badRequest().body("L???i");
        }
        ShoppingCart cart = user.getShoppingCart();
        if(cart == null){
            cart = shoppingCartService.addShoppingCartFirstTime(req, user);
        }else{
            cart = shoppingCartService.addToExistingShoppingCart(req, user);
        }
      
        return ResponseEntity.ok("Th??m th??nh c??ng");

    }

    @PutMapping("/client/api/update-cart")
    public ResponseEntity<?> updateItemCart(@RequestBody AddProductToCartReq req){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        ShoppingCart cart = user.getShoppingCart();
        cart = shoppingCartService.updateItemShoppingCart(req, user);
        return ResponseEntity.ok("C???p nh???t th??nh c??ng");
    }


    @DeleteMapping("/client/api/delete-item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable long id){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        ShoppingCart cart = user.getShoppingCart();
        cart = shoppingCartService.deleteItemShoppingCart(id, user);
        return ResponseEntity.ok("X??a th??nh c??ng");
    }

    @DeleteMapping("/client/api/clear-cart")
    public ResponseEntity<?> clearCart(){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        ShoppingCart cart = user.getShoppingCart();
        shoppingCartService.clearCart(cart.getId(), user);
        return ResponseEntity.ok("X??a th??nh c??ng");
    }
 }
