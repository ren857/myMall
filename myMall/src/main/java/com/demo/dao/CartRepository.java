package com.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.dto.CartItemDTO;
import com.demo.model.Cart;
import com.demo.model.CartId;

public interface CartRepository extends JpaRepository<Cart,CartId> {
	 @Query("SELECT new com.demo.dto.CartItemDTO(p.pname, p.price, p.size, p.image, m.name, c.id.pid, c.quantity) " +
	           "FROM Cart c " +
	           "JOIN Member m ON c.id.mid = m.mid " +
	           "JOIN Product p ON c.id.pid = p.pid " +
	           "WHERE c.id.mid = :mid")
	    List<CartItemDTO> findCartDetails(@Param("mid") Integer mid);	
	 	Optional<Cart> findById(CartId id);
	 	List<Cart> findByIdMid(Integer mid);
	 	void deleteById(CartId id);
    
}