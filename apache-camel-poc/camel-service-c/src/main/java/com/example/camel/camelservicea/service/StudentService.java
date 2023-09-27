package com.example.camel.camelservicea.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.camel.camelservicea.model.Student;

@Service
public interface StudentService {
	
	Student getStudent(int id);
	List<Student> getStudents();
	Student addStudent(Student student);
	

}
