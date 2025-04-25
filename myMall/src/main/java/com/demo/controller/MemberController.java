package com.demo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.demo.dao.MemberRepository;
import com.demo.model.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class MemberController {
	@Autowired
	private MemberRepository mr;


	@RequestMapping("/memberadd")
	public ModelAndView memberadd() {
		return new ModelAndView("memberAdd");
	}

	@RequestMapping("/memberLogin")
	public ModelAndView memberlogin() {
		return new ModelAndView("memberLogin");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member m) {
		Member l = mr.queryMember(m.getLoginusername(), m.getLoginpassword());
		if (l != null) {
			String jwt = generateJwtToken(l); // 生成 JWT Token
			System.out.println("生成的 JWT Token: " + jwt);
			return ResponseEntity.ok(Map.of("token", jwt,"name",l.getName(),"mid",l.getMid())); // 返回 Token
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("帳號密碼錯誤，請重新輸入");
		}
	}

	private String generateJwtToken(Member member) {
		System.out.println(member);
	    return Jwts.builder()
	    		.claim("mid", member.getMid()) 
	            .setSubject(member.getLoginusername())  // 設置 JWT 主題為使用者名稱
	            .setIssuedAt(new Date())  // 設置發行時間
	            .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))  // 設置過期時間，這裡是一個24小時（86400000毫秒）
	            .signWith(SignatureAlgorithm.HS512,"your-secret-key")  // 使用 HS512 算法和秘鑰簽名
	            .compact();  // 壓縮並返回最終的 JWT 字串
	}
//	 public String extractUsername(String token) {
//	        return Jwts.parser()
//	                .setSigningKey("SECRET")
//	                .parseClaimsJws(token)
//	                .getBody()
//	                .getSubject();
//	    }
	 
	 
	@GetMapping("/currentUser")
	public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
	    try {
	    	System.out.println("Authorization Header: " + token);
	    	 // 移除 "Bearer " 前綴並解析 JWT token
	    	String username = Jwts.parser()
	                .setSigningKey("your-secret-key")
	                .parseClaimsJws(token.replace("Bearer ", ""))  // 移除 "Bearer " 部分
	                .getBody()
	                .getSubject();
	        Member member = mr.queryUsername(username);  // 根據 username 查找用戶
	        
	        
	        if (member != null) {
	            return ResponseEntity.ok(Map.of("name", member.getName(), "mid", member.getMid())); // 返回用戶信息
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用戶不存在");
	        }
	    } catch (Exception e) {
	    	System.out.println("JWT 驗證錯誤：" + e.getMessage());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("無效的Token");
	    }
	}

	// 登出，登出後清除 session 並返回首頁
	@GetMapping("/logout")
	public void logout(HttpServletResponse response) throws IOException {
		response.setHeader("Authorization", null); // 這裡清除 token
		response.sendRedirect("/index.html");
	}

	// 註冊會員
	@PostMapping("/addMember")
	public ResponseEntity<String> addMember(@RequestBody Member m) {
		System.out.println(m);
		Member l = mr.queryUsername(m.getLoginusername());
		if (l != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("帳號重複，請重新註冊！");
		} else {
			mr.save(m);
			return ResponseEntity.ok("註冊成功！");
		}
	}

	@RequestMapping("updatepasswordError")
	public String updatepasswordError() {
		return "無此帳號請重新輸入";
	}
}
