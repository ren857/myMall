package com.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
	    private Integer oid;  
	    private Integer pid; 
	    private String pname; 
	    private Integer quantity; 
	    private Integer price; 
	    private String size; 
	    private LocalDateTime orderdate;

}
