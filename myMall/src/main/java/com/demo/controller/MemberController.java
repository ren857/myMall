package com.demo.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.dao.MemberRepository;
import com.demo.model.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class MemberController {
	@Autowired
	private MemberRepository mr;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/memberadd")
	public ModelAndView memberadd() {
		return new ModelAndView("memberAdd");
	}
	@RequestMapping("/memberLogin")
	public ModelAndView memberlogin() {
		return new ModelAndView("memberLogin");
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member m, HttpSession session) {
        Member l = mr.queryMember(m.getLoginusername(), m.getLoginpassword());
        if (l != null) {
            session.setAttribute("M", l);
            session.setAttribute("mid", l.getMid());
            return ResponseEntity.ok(l); // 回傳會員資料
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("帳號密碼錯誤，請重新輸入");
        }
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Member m = (Member) session.getAttribute("M");      
        if (m != null) {
            return ResponseEntity.ok(Map.of("name", m.getName(),"mid", m.getMid())); // 回傳登入帳號名稱
        } else {
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("尚未登入");  // 返回未登入的錯誤訊息
        }
    }

    @GetMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("/index.html");
    }
    @PostMapping("/addMember")
    @ResponseBody
	public ResponseEntity<String> addMember(@RequestBody Member m)
	{	
		System.out.println(m);
		Member l = mr.queryUsername(m.getLoginusername());
		if(l !=null)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("帳號重複，請重新註冊！");
		}
		else
		{
			mr.save(m);
			return ResponseEntity.ok("註冊成功！");
		}
	}	
	@RequestMapping("updatepasswordError")
	public String updatepasswordError() {
		return "無此帳號請重新輸入";
	}
}

