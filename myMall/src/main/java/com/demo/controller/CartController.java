package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.demo.dto.CartItemDTO;
import com.demo.model.Cart;
import com.demo.service.CartService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartController {

	@Autowired
	private CartService cartService;

	// 從 Token 中提取 mid
	private Integer getMemberIdFromToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey("your-secret-key").parseClaimsJws(token.replace("Bearer ", ""))
					.getBody();
			Integer mid = (Integer) claims.get("mid");
			return mid; // 取出 user 的 mid
		} catch (Exception e) {
			return null; // 如果 Token 不合法或解析失敗，返回 null
		}
	}

	@GetMapping("/cart")
	public List<CartItemDTO> getCartItems(@RequestHeader("Authorization") String token) {
		Integer mid = getMemberIdFromToken(token); // 從 Token 中解析 mid
		System.out.println("123" + token);
		System.out.println(mid);
		if (mid == null) {
			return new ArrayList<>(); // 若無法解析出 mid，返回空數組
		}
		return cartService.getCartItemsByMember(mid); // 根據 mid 查詢購物車商品
	}

	@PostMapping("/add")
	public ResponseEntity<String> addToCart(@RequestHeader("Authorization") String token, @RequestParam Integer pid,
			@RequestParam Integer quantity) {
		Integer mid = getMemberIdFromToken(token); // 從 Token 中解析 mid
		System.out.println("mid: " + mid);
		System.out.println("pid: " + pid);
		System.out.println("quantity: " + quantity);
		System.out.println("token: " + getMemberIdFromToken(token));
		if (mid == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入！"); // 未登入的錯誤處理
		}
		cartService.addToCart(mid, pid, quantity); // 使用者已登入，執行加入購物車操作
		return ResponseEntity.ok("商品已加入購物車！");
	}

	@PostMapping("/{mid}")
	public List<Cart> getCart(@PathVariable Integer mid) {
		return cartService.getCartByMember(mid);
	}

	@DeleteMapping("/remove")
	public ResponseEntity<String> removeFromCart(@RequestHeader("Authorization") String token,
			@RequestBody CartItemDTO removeobj) {
		System.out.println("removeobj"+removeobj);
		Integer mid = getMemberIdFromToken(token); // 從 Token 中解析 mid

		if (mid == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入！"); // 未登入的錯誤處理
		}
		cartService.removeFromCart(mid, removeobj.getPid()); // 根據 mid 和 pid 刪除對應商品
		return ResponseEntity.ok("已從購物車移除！");
	}

	@PostMapping("/updateQuantity")
	public ResponseEntity<String> updateQuantity(@RequestHeader("Authorization") String token,
			@RequestBody List<CartItemDTO> cartList) {
		Integer mid = getMemberIdFromToken(token); // 從 Token 中解析 mid
		System.out.println("MID:" + mid);
		System.out.println("cartList:" + cartList);
		for (CartItemDTO c : cartList)
			cartService.addToCart(mid, c.getPid(), c.getQuantity());
		if (mid == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入！"); // 未登入的錯誤處理
		}
		try {
			// cartService.updateQuantity(mid, pid, quantity); // 更新購物車商品數量
			return ResponseEntity.ok("更新訂單！");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("數量更新失敗！");
		}
	}

	@RequestMapping("/cartPage")
	public ModelAndView gotoCart() {
		return new ModelAndView("cart");
	}
}
/*
 * @PostMapping("/checkout") public ResponseEntity<String> checkout(@RequestBody
 * CheckoutRequest checkoutRequest, @RequestHeader("Authorization") String
 * token) { try { // 解析 Token，從中獲取用戶ID（mid） String username =
 * getUsernameFromToken(token); Member member =
 * memberRepository.findByUsername(username);
 * 
 * if (member == null) { return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用戶不存在或未登入"); }
 * 
 * // 處理結帳邏輯 Double totalAmount = checkoutRequest.getTotalAmount();
 * List<CartItem> cartItems = checkoutRequest.getCartItems();
 * 
 * // 儲存訂單 Order order = new Order(); order.setMember(member);
 * order.setTotalAmount(totalAmount); order.setStatus("已支付");
 * orderRepository.save(order);
 * 
 * // 儲存訂單項目 for (CartItem item : cartItems) { OrderItem orderItem = new
 * OrderItem(); orderItem.setOrder(order);
 * orderItem.setProduct(productRepository.findById(item.getProductId()).
 * orElseThrow(() -> new RuntimeException("商品不存在")));
 * orderItem.setQuantity(item.getQuantity());
 * orderItem.setPrice(item.getPrice()); orderItemRepository.save(orderItem); }
 * 
 * // 清空購物車 cartService.clearCart(member.getId());
 * 
 * return ResponseEntity.ok("結帳成功，訂單已處理！"); } catch (Exception e) { return
 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("結帳處理錯誤：" +
 * e.getMessage()); } }
 */
