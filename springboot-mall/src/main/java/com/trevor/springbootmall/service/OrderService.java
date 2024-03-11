package com.trevor.springbootmall.service;

import com.trevor.springbootmall.dto.CreateOrderRequest;
import com.trevor.springbootmall.dto.OrderQueryParams;
import com.trevor.springbootmall.model.Order;

import javax.swing.*;
import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders (OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer creaOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
