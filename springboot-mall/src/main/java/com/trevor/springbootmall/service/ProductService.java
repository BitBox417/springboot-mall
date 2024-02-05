package com.trevor.springbootmall.service;

import com.trevor.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

public interface ProductService {

    Product getProductById(Integer productId);
}
