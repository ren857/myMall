package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Integer>{

}
