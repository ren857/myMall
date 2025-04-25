package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.model.Member;

public interface MemberRepository	extends JpaRepository<Member,Integer> {

	@Query("SELECT m FROM Member m WHERE m.loginusername = ?1")
	Member queryUsername(String loginusername);
	@Query("SELECT m FROM Member m WHERE m.loginusername = ?1 AND m.loginpassword = ?2")
	Member queryMember(String loginusername, String loginpassword);

}
