package com.example.dbtest.domain.service;


import java.util.List;
import java.util.Optional;

import com.example.dbtest.controllers.TaskForm;
import com.example.dbtest.domain.entity.Task;

public interface TaskService {

	List<Task> findAll();
		
	Optional<TaskForm> getTaskForm(int id);

	void save(Task task);
	
	void deleteById(int id);
}
