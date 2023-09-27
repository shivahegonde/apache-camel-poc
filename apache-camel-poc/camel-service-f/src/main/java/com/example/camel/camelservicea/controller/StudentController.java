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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private StudentRestConsumer consumer;
	
	@Autowired
    Environment environment;
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);

	 	
	@GetMapping("/data")
    public String getStudentInfo() {
       logger.info("Accessing from STUDENT-SERVICE ==>" +consumer.getData());
       return "Accessing from STUDENT-SERVICE ==> " +consumer.getData();
    }

    @GetMapping("/allStudents")
    public String getStudentsInfo() {
    	logger.info("Accessing from STUDENT-SERVICE ==> " +consumer.getData());
       return "Accessing from STUDENT-SERVICE ==> " + consumer.getAll();
    }

    @GetMapping("/hello")
    public String printEntityData() {
       ResponseEntity<String> resp = consumer.getEntityData();
       logger.info("Accessing from STUDENT-SERVICE ==> " + resp.getBody() +" , status is:" + resp.getStatusCode());
       return "Accessing from STUDENT-SERVICE ==> " + resp.getBody() +" , status is:" + resp.getStatusCode();
    }
    
    @RequestMapping(value = "/students/add", method = RequestMethod.POST)
	public String addStudent(@RequestBody Student student){
    	
    	ResponseEntity<Student> newStudent = consumer.addStudent(student);
    	logger.info("Student added using feign client");
      	return ""+newStudent.getBody()+" "+" added" ;
    	
    }
	
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public List<Student> allStudents(){
		ResponseEntity<List<Student>> students = consumer.allStudents();
		logger.info("Students retrieved using feign client from DB");
      	return students.getBody();
	}
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public Student getStudentByID(@PathVariable int id){
		ResponseEntity<Student> newStudent = consumer.getStudentByID(id);
		logger.info("Student retrieved using feign client from DB with ID = "+id);
      	return newStudent.getBody();
	}


}
