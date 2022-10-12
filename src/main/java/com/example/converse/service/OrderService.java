package com.example.converse.service;

import org.springframework.data.domain.Page;

import com.example.converse.entity.Order;
import com.example.converse.entity.User;
import com.example.converse.payload.request.CreateOrderReq;

public interface OrderService {

    Page<Order> getListOrder(Integer pageNo,Integer pageSize,String sortBy);

    void saveOrder(User user,CreateOrderReq  req);
}
