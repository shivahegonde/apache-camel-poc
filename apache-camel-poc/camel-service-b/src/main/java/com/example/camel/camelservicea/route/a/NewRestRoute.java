package com.example.camel.camelservicea.route.a;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.camel.camelservicea.model.StudentData;
import com.example.camel.camelservicea.model.Student;
import com.example.camel.camelservicea.processor.StudentProcessor;
import com.example.camel.camelservicea.repo.StudentRepository;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NewRestRoute extends RouteBuilder{
		
	Student s;
	@Override
	public void configure() throws Exception {
	
		from("jms:queue:outQueue")
		.log(LoggingLevel.INFO,"jms:queue:outQueue> from outQueue to StudentQueue")
		.process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				ObjectMapper objectMapper = new ObjectMapper();
				String old = xchg.getIn().getBody(String.class);
				Student newStudent = objectMapper.readValue(old,Student.class);
				StudentData jmsInsertionObject = new StudentData();
				jmsInsertionObject.setStudent(newStudent);
				jmsInsertionObject.setPresent(true);
				 
				String pojoAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jmsInsertionObject);
				xchg.getMessage().setBody(pojoAsString);
				
			}
		})
		.log(LoggingLevel.INFO,"${body}")
		.to("jms:queue:StudentQueue")
		.end();
		
		from("jms:queue:StudentQueue")
		.log(LoggingLevel.INFO,"jms:queue:outQueue> from outQueue to JPA/DB")
		.process(new StudentProcessor())
        .to("jpa:com.example.camel.camelservicea.model.StudentData");
		
		
		
	
		
//		JaxbDataFormat jxb = new JaxbDataFormat();
//        jxb.setContextPath("com.example.camel.camelservicea.model.Student");
//
//        from("file:src/data?noop=true")
//        .process(new Processor() {
//			public void process(Exchange xchg) throws Exception {
//				ObjectMapper objectMapper = new ObjectMapper();
//				String old = xchg.getIn().getBody(String.class);
//				Student newStudent = objectMapper.readValue(old,Student.class);
//				
//				xchg.getMessage().setBody(pojoAsString);
//				
//			}
//		})
//                .to("jpa:com.example.camel.camelservicea.model.Student");
//		rest("masterClass")
//			.produces("application/json")
//			.post("nameAddress").type(NameAddress.class).route()
//			.routeId("newRestRouteId")
//			.log(LoggingLevel.INFO,"${body}")
//			.process(new InboundMessageProcessor())
//			.log(LoggingLevel.INFO, "Transformed Body : ${body}")
//			.convertBodyTo(String.class)
//			.to("file:data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");
//		
	}

}
