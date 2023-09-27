package com.example.camel.camelservicea.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.example.camel.camelservicea.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentProcessor implements Processor{
	
		@Override
		public void process(Exchange xchg) throws Exception {
			ObjectMapper objectMapper = new ObjectMapper();
			String old = xchg.getIn().getBody(String.class);
			Student newStudent = objectMapper.readValue(old,Student.class);
			 
			System.out.println("Student = "+newStudent);
			
			xchg.getMessage().setBody(newStudent);
			
		}
		public StudentProcessor() {
	    }
}
