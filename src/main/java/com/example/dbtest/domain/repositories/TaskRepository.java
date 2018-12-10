package com.example.dbtest.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dbtest.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	Optional<Task> findById(int id);
	void deleteById(int id);
}
