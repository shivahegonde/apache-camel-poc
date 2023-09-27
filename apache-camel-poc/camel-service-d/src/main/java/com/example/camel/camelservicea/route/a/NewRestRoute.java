package com.example.camel.camelservicea.route.a;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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
	RestTemplate restTemplate;
	
	private final String STUDENT_URL = "/students";
	
	Student s;
	@Override
	public void configure() throws Exception {
		
		
		restConfiguration()
	 	.component("jetty")
	 	.host("127.0.0.1")
	 	.port(8085)
	 	.bindingMode(RestBindingMode.json)
	 	.enableCORS(true);
	
		
		rest("/api/v1")
		.produces("application/json")
		.get("/allStudents").route()
		.log(LoggingLevel.INFO,"${body}")
		.setBody().constant(restTemplate.getForObject(STUDENT_URL, Student[].class))
        .end()
        .endRest();
//		
		rest("/api/v1/student")
		.produces("application/json")
		.post("add").type(Student.class).route()
		.routeId("newRestRouteId")
		.log(LoggingLevel.INFO,"${body}")
		.process(new Processor() {
			public void process(Exchange xchg) throws Exception {
				s = xchg.getIn().getBody(Student.class);
				restTemplate.postForObject(STUDENT_URL+"/add", s, Student.class);
				xchg.getMessage().setBody(s);
			}
		})
//		
		.to("direct:jmsOps")
		.setBody(constant("Data sent to ActiveMQ"))
        .end()
        .endRest();
//		
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
		.to("jms:queue:jmsQueue?disableReplyTo=true");
     			
	}

}
