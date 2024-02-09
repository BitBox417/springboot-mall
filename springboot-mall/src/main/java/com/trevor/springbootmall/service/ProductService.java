package com.trevor.springbootmall.service;

import com.trevor.springbootmall.dto.ProductRequest;
import com.trevor.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductService {


    List<Product> getProducts();
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
