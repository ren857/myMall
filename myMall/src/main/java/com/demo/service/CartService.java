package com.demo.service;

import java.util.List;

import com.demo.dto.CartItemDTO;
import com.demo.model.Cart;


public interface CartService {
	List<CartItemDTO> getCartItemsByMember(Integer mid);
	void addToCart(Integer mid, Integer pid, Integer quantity);
	List<Cart> getCartByMember(Integer mid);
	void removeFromCart(Integer mid, Integer pid);
	void updateQuantity(Integer mid, Integer pid, Integer quantity) throws Exception;
    
}
