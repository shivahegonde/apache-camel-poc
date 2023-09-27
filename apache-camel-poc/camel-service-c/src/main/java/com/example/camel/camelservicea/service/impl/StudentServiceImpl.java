package com.example.camel.camelservicea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.camel.camelservicea.model.Student;
import com.example.camel.camelservicea.repo.StudentRepository;
import com.example.camel.camelservicea.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	StudentRepository repo;
	
	@Override
	public Student getStudent(int id) {
		// TODO Auto-generated method stub
		
		return repo.findById(id).get();
	}

	@Override
	public List<Student> getStudents() {
		
		return repo.findAll();
	}

	@Override
	public Student addStudent(Student student) {
		// TODO Auto-generated method stub
		return repo.save(student);
	}

}
