package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		return new ModelAndView("memberadd");
	}
	@RequestMapping("/memberLogin")
	public ModelAndView memberlogin() {
		return new ModelAndView("memberLogin");
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@RequestBody Member m) {
		System.out.println(m);
		Member l = mr.queryMember(m.getLoginusername(),m.getLoginpassword());
		System.out.println(l);
		if(l!=null) {
			session.setAttribute("M", l);
			ModelAndView mav = new ModelAndView("index");
			return mav;
		}
		else
		{
			return new ModelAndView("redirct:errorLogin");
		}
	}

}
