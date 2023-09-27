package com.example.camel.camelservicea.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@Autowired
    Environment environment;
	
	Logger logger = LoggerFactory.getLogger(StudentController.class);

	@RequestMapping(value = "/status",method = RequestMethod.GET)
    public String getData() {
		
		logger.info("data of STUDENT-SERVICE, Running on port: "
		         +environment.getProperty("local.server.port"));
		
       return "data of STUDENT-SERVICE, Running on port: "
         +environment.getProperty("local.server.port");
    }
	
	@GetMapping("/{id}")
    public Student getById(@PathVariable Integer id) {
		Student s = new Student(id, "Akash","Pune","23");
		logger.info("Returning Dummy Student "+s.getName());
      	return s;
    }

    @GetMapping("/all")
    public List<Student> getAll(){
    	List<Student> students = List.of(
                new Student(1, "Akash","Pune","23"),
                new Student(2, "Shiv","Pune, MH","25"),
                new Student(3, "Raj","Pune, MH","26")
         );
    	logger.info("Returning Dummy Students "+students);
    	return students;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getEntityData() {
    	logger.info("hello api for checking status ");
    	return new ResponseEntity<String>("Hello from StudentRestController", HttpStatus.OK);
    }
	
	@RequestMapping(value = "/students/add", method = RequestMethod.POST)
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
	     
		Student newStudent = service.addStudent(student);
		logger.info("Student saved in DB/ActiveMQ");
      	return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
	      	
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> allStudents() {
		List<Student> students = service.getStudents();
		logger.info("Students retrieved from DB/ActiveMQ");
	    return new ResponseEntity<>(students, HttpStatus.OK);
	      	
	}
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public ResponseEntity<Student> getStudentByID(@PathVariable int id) {
	     
		Student newStudent = service.getStudent(id);
		logger.info("Student retrieved from DB/ActiveMQ");
      	return new ResponseEntity<>(newStudent, HttpStatus.OK);
	      	
	}

}
