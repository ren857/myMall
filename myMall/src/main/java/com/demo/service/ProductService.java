package com.demo.service;

import java.util.List;

import com.demo.model.Product;

public interface ProductService {
    Product getProductById(Integer pid);
    List<Product> getAllProducts();  
    List<Product> findProductByPname(String pname);
    List<String>getProductListByGruopName();
    List<Integer>getPidByPname(String pname);
}


    