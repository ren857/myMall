package com.demo.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CartController {

	@RequestMapping("/cartPage")
	public ModelAndView gotoCart() {
		return new ModelAndView("cart");
	}
}

