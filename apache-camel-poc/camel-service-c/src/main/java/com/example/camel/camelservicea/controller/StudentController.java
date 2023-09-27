package com.example.camel.camelservicea.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.camel.camelservicea.model.Student;
import com.example.camel.camelservicea.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	StudentService service;
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);

	
	@RequestMapping(value = "/students/add", method = RequestMethod.POST)
	public ResponseEntity<Student> addStudents(@RequestBody Student student) {
	     
			Student newStudent = service.addStudent(student);
			logger.info("Student saved in DB/ActiveMQ");
	      	return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
	      	
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> addStudents() {
	     
			List<Student> students = service.getStudents();
			logger.info("Students retrieved from DB/ActiveMQ");
	      	return new ResponseEntity<>(students, HttpStatus.OK);
	      	
	}
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public ResponseEntity<Student> getStudentByID(@PathVariable int id) {
	     
			Student newStudent = service.getStudent(id);
			
			logger.info("Students retrieved from DB/ActiveMQ with id = "+id);
	      	return new ResponseEntity<>(newStudent, HttpStatus.OK);
	      	
	}

}
