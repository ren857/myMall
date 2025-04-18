package com.demo.service;

import java.util.List;

import com.demo.model.Product;

public interface ProductService {
    Product getProductById(Integer pid);
    List<Product> getAllProducts();
}