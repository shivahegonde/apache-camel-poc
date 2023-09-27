package com.example.camel.camelservicea.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.camel.camelservicea.model.Student;

@FeignClient(name="STUDENT-SERVICE")
public interface StudentRestConsumer {
	
	@RequestMapping(value = "/status",method = RequestMethod.GET)
    public String getData();
	
	@GetMapping("/{id}")
    public Student getById(@PathVariable Integer id);

    @GetMapping("/all")
    public List<Student> getAll();

    @GetMapping("/hello")
    public ResponseEntity<String> getEntityData() ;
	
	@RequestMapping(value = "/students/add", method = RequestMethod.POST)
	public ResponseEntity<Student> addStudent(@RequestBody Student student);
	
	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ResponseEntity<List<Student>> allStudents();
	
	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public ResponseEntity<Student> getStudentByID(@PathVariable int id);

}
