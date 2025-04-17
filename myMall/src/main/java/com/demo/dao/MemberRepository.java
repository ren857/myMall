package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.model.Member;

public interface MemberRepository	extends JpaRepository<Member,Integer> {


	@Query(value="select * from member where username=?1",nativeQuery=true)
	Member queryUsername(String username);
	
	
	@Query(value="select * from member where username=?1 and password=?2",nativeQuery=true)
	Member queryMember(String username,String password);
	
}
