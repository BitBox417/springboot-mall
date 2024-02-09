package com.trevor.springbootmall.dao;

import com.trevor.springbootmall.constant.ProductCategory;
import com.trevor.springbootmall.dto.ProductRequest;
import com.trevor.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category,String search);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);

}
