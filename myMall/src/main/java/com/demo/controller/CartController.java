package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.demo.dao.CartRepository;
import com.demo.dto.CartItemDTO;
import com.demo.model.Cart;
import com.demo.service.CartService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private HttpSession session;

	@GetMapping("/cart")
	public List<CartItemDTO> getCartItems(HttpSession session) {
	    // 確保 Session 中的 mid（會員 ID）存在
	    Integer mid = (Integer) session.getAttribute("mid");
	    System.out.println(mid);
	    if (mid == null) {
	        return new ArrayList<>();  // 如果沒有 mid，返回空數組
	    } return cartService.getCartItemsByMember(mid);
    }
	    
	   
	@PostMapping("/add")
	public ResponseEntity<String> addToCart(@RequestParam Integer mid, @RequestParam Integer pid,@RequestParam Integer quantity) {
			cartService.addToCart(mid, pid,quantity);
			return ResponseEntity.ok("商品已加入購物車！");	
	}

	@PostMapping("/{mid}")
	public List<Cart> getCart(@PathVariable Integer mid) {
		return cartService.getCartByMember(mid);
	}
	@DeleteMapping("/remove")
	public ResponseEntity<String> removeFromCart(@RequestParam Integer pid, HttpSession session) {
		
		Integer mid=(Integer) (session.getAttribute("mid"));
		System.out.println(session.getAttribute("mid"));		
	    cartService.removeFromCart(mid,pid);  // 調用服務層刪除
	    return ResponseEntity.ok("已從購物車移除！");  // 返回刪除成功訊息
	}
	@RequestMapping("/cartPage")
	public ModelAndView gotoCart(){
		return new ModelAndView("cart");
	}
	 @PostMapping("/updateQuantity")
	    public ResponseEntity<String> updateQuantity(@RequestParam Integer mid, @RequestParam Integer pid, @RequestParam Integer quantity) {
		 System.out.println("更新數量 -> mid: " + mid + ", pid: " + pid + ", quantity: " + quantity);
	        try {
	            cartService.updateQuantity(mid, pid, quantity);  // 更新數量	            
	            return ResponseEntity.ok("數量已更新！");	            
	        } catch (Exception e) {	        	
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("數量更新失敗！");
	        }
	    }
}
