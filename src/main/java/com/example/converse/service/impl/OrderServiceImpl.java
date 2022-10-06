package com.example.converse.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.Order;
import com.example.converse.entity.OrderDetail;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.repository.CartItemRepository;
import com.example.converse.repository.OrderDetailRepository;
import com.example.converse.repository.OrderRepository;
import com.example.converse.repository.ShoppingCartRepository;
import com.example.converse.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveOrder(User user) {
        // TODO Auto-generated method stub
        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setRequiredDate(new Date());
        order.setUser(user);
        ShoppingCart cart = user.getShoppingCart();
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        for(CartItem item : cartItems){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setPrice(item.getPrice());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setTotalPrice(item.getTotalPrice());
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
            cartItemRepository.delete(item);
        }
        orderRepository.save(order);
    }
    
}
