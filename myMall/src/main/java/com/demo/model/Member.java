package com.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		@Entity
		@Table(name="member")
  		public class Member {
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			Integer mid;
			String name;
			String loginusername;
			String loginpassword;
			String phone;
	
	
}
