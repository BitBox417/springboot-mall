package com.trevor.springbootmall.dao;

import com.trevor.springbootmall.dto.ProductRequest;
import com.trevor.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer productId,ProductRequest productRequest);

}
