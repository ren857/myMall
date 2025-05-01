package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartItemDTO {
    private Integer pid;
    private Integer quantity;
    private Integer price;
    private String pname;
    private String size;


   
}
