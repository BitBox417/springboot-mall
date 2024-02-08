package com.trevor.springbootmall.service.impl;

import com.trevor.springbootmall.dao.ProductDao;
import com.trevor.springbootmall.dto.ProductRequest;
import com.trevor.springbootmall.model.Product;
import com.trevor.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {

        return productDao.getProductById(productId);
    }


    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
