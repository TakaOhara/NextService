package com.example.dbtest.domain.service;

import java.util.List;
import java.util.Optional;

import com.example.dbtest.controllers.InstructorForm;
import com.example.dbtest.domain.entity.Instructor;

public interface InstructorService {

	List<Instructor> findAll();
		
	Optional<InstructorForm> getInstructorForm(int id);

	void save(Instructor instructor);
	
	void deleteById(int id);
}
