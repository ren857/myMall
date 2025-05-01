package com.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.dao.OrderRepository;
import com.demo.dao.OrderdetailsRepository;
import com.demo.dao.ProductRepository;
import com.demo.dto.CartItemDTO;
import com.demo.dto.OrderDetailsDTO;
import com.demo.model.Orderdetails;
import com.demo.model.Orders;
import com.demo.model.Product;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderdetailsRepository orderdetailsRepository;

	@Autowired
	private ProductRepository productRepository;


	@Override
	public Orders saveOrder(int mid, List<CartItemDTO> cartItems) {
		Orders order = new Orders();
		order.setMid(mid);
		Orders savedOrder = orderRepository.save(order); // 保存訂單
		System.out.println("結帳cartItems!!:" + cartItems);
		for (CartItemDTO item : cartItems) {
			System.out.println("結帳item1:" + item);
			Orderdetails orderdetails = new Orderdetails();
			orderdetails.setOid(savedOrder.getOid());
			orderdetails.setPid(item.getPid());
			orderdetails.setQuantity(item.getQuantity());
			orderdetailsRepository.save(orderdetails); // 遍歷存儲每一筆訂單
			System.out.println("結帳item2:" + item);
		}

		return savedOrder;
	}

	public List<OrderDetailsDTO> getOrderHistory(Integer mid) {
		// 這裡可以根據 mid 查詢所有的訂單詳細
		List<Orderdetails> orderDetailsList = orderdetailsRepository.findByMid(mid);
		List<OrderDetailsDTO> dtoList = new ArrayList<>();

		for (Orderdetails orderDetail : orderDetailsList) {
			OrderDetailsDTO dto = new OrderDetailsDTO();
			dto.setOid(orderDetail.getOid());
			dto.setPid(orderDetail.getPid());
			dto.setQuantity(orderDetail.getQuantity());
			Product product = productRepository.findById(orderDetail.getPid()).orElse(null);
			if (product != null) {
				dto.setPname(product.getPname());
				dto.setPrice(product.getPrice());
				dto.setSize(product.getSize());
			}
			Orders order = orderRepository.findById(orderDetail.getOid()).orElse(null);
			if (order != null) {
				dto.setOrderdate(order.getOrderdate()); // 設置訂單日期
			}
			dtoList.add(dto);
		}
		return dtoList;
	}
	public List<CartItemDTO> getDetailsByOid(Integer oid) {
        return orderdetailsRepository.findCartItemDTOByOid(oid);
    }


	
}

