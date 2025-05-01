package com.demo.service;

import java.util.List;
import com.demo.dto.CartItemDTO;
import com.demo.dto.OrderDetailsDTO;
import com.demo.model.Orders;

public interface OrderService {
	Orders saveOrder(int mid, List<CartItemDTO> cartItems);

	List<OrderDetailsDTO> getOrderHistory(Integer mid);

	List<CartItemDTO> getDetailsByOid(Integer oid);

}
