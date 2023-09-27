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
	
	@Autowired
	private StudentRepository repo;
	
	Student s;
	
	@Override
	public void configure() throws Exception {
		restConfiguration()
		 	.component("jetty")
		 	.host("127.0.0.1")
		 	.port(8081)
		 	.bindingMode(RestBindingMode.json)
		 	.enableCORS(true);
		
		//demo rest
		rest("hello")
		.produces("application/json")
		.get("shiv").route()
		.log(LoggingLevel.INFO,"${body}")
		.setBody().constant("Hello world")
        .end()
        .endRest();
		
		//Rest to  JMS start
		rest("student")
		.produces("application/json")
		.post("add").type(Student.class).route()
		.routeId("newRestRouteId")
		.log(LoggingLevel.INFO,"${body}")
		.process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				s = xchg.getIn().getBody(Student.class);
				System.out.println("Data from post api is "+s);
				xchg.getMessage().setBody(s);
			}
		})
		
		.to("direct:jmsOps")
		.setBody(constant("Data sent to ActiveMQ"))
        .end()
        .endRest();
		
		from("direct:jmsOps")
		.log("data recieved for jms")
		.process(new Processor() {
			public void process(Exchange xchg) throws Exception {
			
				 s = xchg.getIn().getBody(Student.class);
				 System.out.println("Student = "+s);
				 ObjectMapper objectMapper = new ObjectMapper();
					String pojoAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);
				xchg.getMessage().setBody(pojoAsString);
				
			}
			
		})
		.log("${body}")
		.to("jms:queue:outQueue?disableReplyTo=true");
     		
		
		
		
		

//		from("jms:queue:outQueue")
//		.log("adding to database - ")
//		.process(new Processor() {
//			public void process(Exchange xchg) throws Exception {
//				ObjectMapper objectMapper = new ObjectMapper();
//				String old = xchg.getIn().getBody(String.class);
//				Student newStudent = objectMapper.readValue(old,Student.class);
//				 
//				System.out.println("Student = "+newStudent);
//				 
//				JMSInsertion jmsInsertionObject = new JMSInsertion();
//				jmsInsertionObject.setStudent(newStudent);
//				jmsInsertionObject.setPresent(true);
//				 
//				String pojoAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jmsInsertionObject);
//				xchg.getMessage().setBody(pojoAsString);
//				
//			}
//		})
//		.log("${body}")
//		.to("jms:queue:StudentQueue")
//		.end();
		
//		from("jms:queue:outQueue")
//		.log("adding data from queue to db")
//		.process(new StudentProcessor())
//        .to("jpa:com.example.camel.camelservicea.model.Student");
//		
		
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
