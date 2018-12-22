package com.example.dbtest.domain.service;


import java.util.List;
import java.util.Optional;

import com.example.dbtest.controllers.ProfileForm;
import com.example.dbtest.domain.entity.Profile;

public interface ProfileService {

	List<Profile> findAll();
	
	Optional<Profile> findByUserInfoId(int id);
		
	Optional<ProfileForm> getProfileForm(int userInfoId);

	void save(Profile task);
	
	void deleteById(int userInfoId);

	boolean existsByUserInfoId(int userInfoId);
}
