package com.trevor.springbootmall.service;

import com.trevor.springbootmall.dto.CreateOrderRequest;
import com.trevor.springbootmall.model.Order;

import javax.swing.*;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer creaOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
