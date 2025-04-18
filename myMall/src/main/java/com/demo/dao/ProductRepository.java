package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
   
}
