package com.demo.controller;

import com.demo.dao.OrderRepository;
import com.demo.dto.CartItemDTO;
import com.demo.dto.OrderDetailsDTO;
import com.demo.model.Orders;
import com.demo.service.OrderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@GetMapping
	public ModelAndView memberlogin() {
		return new ModelAndView("order");
	}

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	// 創建訂單（結帳）
	@PostMapping("/submitOrder")
	public ResponseEntity<String> submitOrder(@RequestHeader("Authorization") String token,
			@RequestBody List<CartItemDTO> cartItems) {
		System.out.println("cartItems:" + cartItems);
		Integer mid = getMemberIdFromToken(token);
		Orders order = orderService.saveOrder(mid, cartItems);
		System.out.println("cartItemsHI:" + cartItems);
		System.out.println("結帳order:" + order);
		return ResponseEntity.ok("訂單已成功提交，訂單編號：" + order.getOid());
	}

	// 查詢所有訂單
	@GetMapping("/history")
	public ResponseEntity<List<OrderDetailsDTO>> getOrderHistory(@RequestHeader("Authorization") String token) {
		Integer mid = getMemberIdFromToken(token); // 從 token 中提取會員 id
		if (mid == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 未登入
		}

		// 根據 mid 查詢訂單詳情並返回
		List<OrderDetailsDTO> orderHistory = orderService.getOrderHistory(mid);
		System.out.println("orderHistory:" + orderHistory);
		return ResponseEntity.ok(orderHistory);

	}

	@PostMapping("/details/{oid}")
	public ResponseEntity<List<CartItemDTO>> getProduct(@PathVariable Integer oid) {
		List<CartItemDTO> cartItems = orderService.getDetailsByOid(oid);
		System.out.println("cartItems:" + cartItems);
		return ResponseEntity.ok(cartItems);
	}

	@GetMapping("/getorder")
	public ResponseEntity<List<Orders>> getOrder(@RequestHeader("Authorization") String token) {
		Integer mid = getMemberIdFromToken(token);
		if (mid == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 未登入
		}
		List<Orders> order = orderRepository.findByMid(mid);
		return ResponseEntity.ok(order);
	}

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

	@GetMapping("/details")
	public ModelAndView gotodetails(@RequestParam Integer oid) {
		return new ModelAndView("orderdetails");
	}

}
