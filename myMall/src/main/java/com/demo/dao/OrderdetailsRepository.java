package com.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.dto.CartItemDTO;
import com.demo.model.Orderdetails;

public interface OrderdetailsRepository extends JpaRepository<Orderdetails,Integer>{

	@Query("SELECT od FROM Orderdetails od JOIN Orders o ON od.oid = o.oid WHERE o.mid = :mid")
	List<Orderdetails> findByMid(int mid);
	@Query("SELECT new com.demo.dto.CartItemDTO(od.pid, od.quantity, p.price, p.pname, p.size) " +
		       "FROM Orderdetails od " +
		       "JOIN Product p ON od.pid = p.pid " +
		       "WHERE od.oid = :oid")
		List<CartItemDTO> findCartItemDTOByOid(@Param("oid") Integer oid);



}
