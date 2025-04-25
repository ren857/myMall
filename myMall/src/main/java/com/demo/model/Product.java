package com.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	@Table(name="product")
public class Product {
		@Id
		Integer pid;
		String pname;
		Integer price;
		String image;
		String size;
		String brief;
		
		
}
