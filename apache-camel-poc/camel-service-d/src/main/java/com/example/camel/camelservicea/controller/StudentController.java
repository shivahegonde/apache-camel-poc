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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.camel.camelservicea.model.Student;

@RestController
public class StudentController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
    Environment environment;
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);

	
	private final String STUDENT_URL = "/students";
	private final String STUDENT_URL_WITH_ID = "/students/{id}";
	
	@RequestMapping(value = "/api/v1/allStudents", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> getStudents_v1() {
	
		Student[] studentArray = restTemplate.getForObject(STUDENT_URL, Student[].class);
		logger.info("Students retrieved from DB/ActiveMQ");
		return new ResponseEntity<>(Arrays.asList(studentArray), HttpStatus.OK);
		
	}
	
	@PostMapping("/api/v1/addStudent")
	public ResponseEntity<Student> create(@RequestBody final Student student) {
		Student createdStudent = restTemplate.postForObject(STUDENT_URL+"/add", student, Student.class);
		logger.info("Students saved in DB/ActiveMQ with id = "+student.getId());
		return new ResponseEntity<>(createdStudent,HttpStatus.OK);
	}

}
