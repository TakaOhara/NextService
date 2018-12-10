package com.example.dbtest.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dbtest.domain.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	Optional<Instructor> findById(int id);
	Optional<Instructor> findByEmail(String email);
	void deleteById(int id);
}
