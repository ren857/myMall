package com.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.demo.model.Product;
import com.demo.service.ProductService;

@RequestMapping("/api/products")
@RestController
public class ProductController {


	@Autowired
	private ProductService productservice;

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
	 @GetMapping("/name/{pname}")
	 public List<Product> getProductName(@PathVariable String pname) {
		 return productservice.findProductByPname(pname);
	 }
	 
    // 查詢所有產品
    @GetMapping
    public List<Product> getAllProducts() {
        return productservice.getAllProducts();
    }
    @GetMapping("/listname")
    public ArrayList<Product>getProductListByGroupName(){
    	
    	List<String>namelist=productservice.getProductListByGruopName();
    	ArrayList<Product>productlist = new ArrayList<Product>();
    	for(String name:namelist) {
    		Product p =productservice.findProductByPname(name).get(0);
    		productlist.add(p);
    	}
    	return productlist;
    }
    @GetMapping("/listpid/{pname}")
    public List<Integer>getPidlistByName(@PathVariable String pname){
    	return productservice.getPidByPname(pname);
    }
    @GetMapping("/productlist/{pname}")
    public List<Product>getProductlist(@PathVariable String pname){
    	List<Integer>pidlist=productservice.getPidByPname(pname);
    	ArrayList<Product>productlist = new ArrayList<Product>();
    	for(Integer pid:pidlist) {
    		Product p=productservice.getProductById(pid);
    		productlist.add(p);
    	}
    	return productlist;
    }  
}
