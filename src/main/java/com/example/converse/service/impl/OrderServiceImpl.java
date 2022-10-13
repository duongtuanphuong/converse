package com.example.converse.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.converse.entity.CartItem;
import com.example.converse.entity.Order;
import com.example.converse.entity.OrderDetail;
import com.example.converse.entity.ShoppingCart;
import com.example.converse.entity.User;
import com.example.converse.payload.request.CreateOrderReq;
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
    public void saveOrder(User user,CreateOrderReq req) {
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
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        shoppingCartRepository.save(cart);
        order.setTotalPrices(cart.getTotalPrices());
        order.setTotalItems(cart.getTotalItems());
        order.setName(req.getName());
        order.setEmail(req.getEmail());
        order.setCountry(req.getCountry());
        order.setAddress(req.getAddress());
        order.setPhone(req.getPhone());
        order.setNote(req.getNote());
        orderRepository.save(order);
    }

    @Override
    public Page<Order> getListOrder(Integer pageNo, Integer pageSize, String sortBy) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Order> listOrder = orderRepository.findAll(pageable);
        return listOrder;
    }
    
}
