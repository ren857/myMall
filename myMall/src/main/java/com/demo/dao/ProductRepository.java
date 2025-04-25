package com.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	 @Query("SELECT p FROM Product p WHERE p.pname = :pname")
	    List<Product> findProductByPname(String pname);
	 @Query("SELECT p.pname FROM Product p GROUP BY p.pname")
	    List<String> getProductListByGroupName();
	 @Query("SELECT p.pid FROM Product p WHERE p.pname = :pname")
	    List<Integer> getPidByPname(String pname);
	}

	


