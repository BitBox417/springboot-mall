package com.trevor.springbootmall.service.impl;

import com.trevor.springbootmall.dao.OrderDao;
import com.trevor.springbootmall.dao.ProductDao;
import com.trevor.springbootmall.dao.UserDao;
import com.trevor.springbootmall.dto.BuyItem;
import com.trevor.springbootmall.dto.CreateOrderRequest;
import com.trevor.springbootmall.dto.OrderQueryParams;
import com.trevor.springbootmall.model.Order;
import com.trevor.springbootmall.model.OrderItem;
import com.trevor.springbootmall.model.Product;
import com.trevor.springbootmall.model.User;
import com.trevor.springbootmall.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;




@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OrderDao orderDao;
    @Autowired

    private ProductDao productDao;
    @Autowired
    private UserDao userDao;


    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOderId(order.getOrderId());
            order.setOrderItemList (orderItemList);
        }
        return orderList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOderId(orderId);

        order.setOrderItemList(orderItemList);
        return order;
    }

    @Transactional
    @Override
    public Integer creaOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        //檢查 user 是否存在
        User user = userDao.getUserById(userId);

        if(user == null) {
            log.warn("該 userId {} 不存在", userId);

            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }



        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查 product 是否存在 庫存是否足夠
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘苦存 {} 購買數量 {}",
                        buyItem.getProductId(),product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());






            //計算價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }



//        創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
