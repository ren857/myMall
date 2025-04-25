package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.CartRepository;
import com.demo.dto.CartItemDTO;
import com.demo.model.Cart;
import com.demo.model.CartId;
import com.demo.service.CartService;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Override
	public void addToCart(Integer mid, Integer pid, Integer quantity) {
		CartId id = new CartId(mid, pid);
		Cart cart = new Cart(id, quantity);
		System.out.println("mid1: "+mid);
	    System.out.println("pid1: "+pid);
	    System.out.println("quantity1: "+quantity);
		cartRepository.save(cart);
		cartRepository.flush();
	}

	@Override
	public List<Cart> getCartByMember(Integer mid) {
		return cartRepository.findByIdMid(mid); // ✅ 用複合主鍵中的 mid 查詢
	}

	@Override
	public void removeFromCart(Integer mid, Integer pid) {
		CartId id = new CartId(mid, pid);
		cartRepository.deleteById(id);
	}

	@Transactional
	@Override
	public List<CartItemDTO> getCartItemsByMember(Integer mid) {
		// 通過 CartRepository 查詢資料庫並返回購物車資料
		List<CartItemDTO> cartItems = cartRepository.findCartDetails(mid);
		System.out.println("返回的购物车内容：" + cartItems); // 打印查询结果
		return cartItems;

	}

	@Transactional
	@Override
	public void updateQuantity(Integer mid, Integer pid, Integer quantity) throws Exception {
	    try {
	        Cart cart = cartRepository.findById(new CartId(mid, pid)).orElseThrow(() -> new Exception("商品不存在"));
	        cart.setQuantity(quantity);  // 更新數量
	        cartRepository.save(cart);  // 保存更新
	    } catch (Exception e) {
	        System.out.println("錯誤訊息: " + e.getMessage());  // 輸出錯誤訊息
	        throw e;  // 重新拋出異常，以便捕捉並返回給客戶端
	    }
	}

}
