package com.trevor.springbootmall.dao;

import com.trevor.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
