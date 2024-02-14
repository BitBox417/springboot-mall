package com.trevor.springbootmall.service;

import com.trevor.springbootmall.dto.CreateOrderRequest;

import javax.swing.*;

public interface OrderService {

    Integer creaOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
