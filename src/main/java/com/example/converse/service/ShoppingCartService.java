package com.example.converse.service;

import java.util.List;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.Product;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.AddProductToCartReq;

public interface ShoppingCartService {


    ShoppingCart addShoppingCartFirstTime(AddProductToCartReq req,User user);

    ShoppingCart addToExistingShoppingCart(AddProductToCartReq req,User user);

    List<CartItem> getListCartItem(long id); 

    ShoppingCart updateItemShoppingCart(AddProductToCartReq req,User user);

    ShoppingCart deleteItemShoppingCart(long id,User user);

    void clearCart(long id,User user);
}
