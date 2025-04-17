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
		@Table(name="member")
  		public class Member {
			@Id
			Integer mid;
			String loginusername;
			String loginpassword;
			String phone;
	
	
}
