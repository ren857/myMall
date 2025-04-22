package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.demo.dao.ProductRepository;
import com.demo.model.Member;
import com.demo.model.Product;
import com.demo.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductRepository productRepository;

	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductService productservice;

    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	
	@RequestMapping("/shop")
	public ModelAndView shop()
	{
		return new ModelAndView("shop");
	}
	@RequestMapping("/detail")
	public ModelAndView detailPage() {
	    return new ModelAndView("product-detail");
	}
	
	 @GetMapping("/{pid}")
    public Product getProduct(@PathVariable Integer pid) {
        return productservice.getProductById(pid);
    }

    // 查詢所有產品
    @GetMapping
    public List<Product> getAllProducts() {
        return productservice.getAllProducts();
    }
    
}
