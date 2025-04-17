package com.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.model.Member;

public interface MemberRepository	extends JpaRepository<Member,Integer> {


	@Query(value="select * from member where loginusername=?1",nativeQuery=true)
	Member queryUsername(String loginusername);
	
	
	@Query(value="select * from member where loginusername=?1 and loginpassword=?2",nativeQuery=true)
	Member queryMember(String loginusername,String loginpassword);
	
}
