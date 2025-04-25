package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Product;
import com.demo.dao.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    
    @Override
    public Product getProductById(Integer pid) {
        return productRepository.findById(pid)
                .orElseThrow(() -> new RuntimeException("找不到產品 id: " + pid));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

	@Override
	public List<Product> findProductByPname(String pname) {
		
		return productRepository.findProductByPname(pname);
	}

	@Override
	public List<String> getProductListByGruopName() {
		// TODO Auto-generated method stub
		return productRepository.getProductListByGroupName();
	}

	@Override
	public List<Integer> getPidByPname(String pname) {
		
		return productRepository.getPidByPname(pname);
	}
}
