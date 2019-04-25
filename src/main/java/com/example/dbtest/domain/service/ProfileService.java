package com.example.dbtest.domain.service;


import java.util.List;
import java.util.Optional;
import com.example.dbtest.controllers.ProfileForm;
import com.example.dbtest.domain.entity.Profile;

public interface ProfileService {

	List<Profile> findAll();
	
	Optional<Profile> findById(int id);
	
	Optional<Profile> findByUserId(int id);
		
	Optional<ProfileForm> getProfileForm(int id);

	void save(Profile profile);
	
	void deleteById(int id);
}
