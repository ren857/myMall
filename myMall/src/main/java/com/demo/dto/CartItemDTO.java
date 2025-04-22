package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

	private String pname;
	private Integer price;
	private String size;
	private String image;
	private String memberName ;
	private Integer pid;
	private Integer quantity;

}
