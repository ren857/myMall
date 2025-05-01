package com.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer>{
	List<Orders> findByMid(int mid);

}
