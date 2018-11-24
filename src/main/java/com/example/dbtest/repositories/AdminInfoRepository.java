package com.example.dbtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dbtest.entity.AdminInfo;

public interface AdminInfoRepository extends JpaRepository<AdminInfo, String> {
	
	AdminInfo findByEmail(String email);

}
