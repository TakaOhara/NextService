package com.example.dbtest.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dbtest.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	User findById(int id);
	User findByEmail(String email);
	User findByTempkey(String tempkey);

}
