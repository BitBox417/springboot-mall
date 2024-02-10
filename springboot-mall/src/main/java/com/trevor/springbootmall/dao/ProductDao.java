package com.trevor.springbootmall.dao;

import com.trevor.springbootmall.constant.ProductCategory;
import com.trevor.springbootmall.dto.ProductQueryParams;
import com.trevor.springbootmall.dto.ProductRequest;
import com.trevor.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);

}
