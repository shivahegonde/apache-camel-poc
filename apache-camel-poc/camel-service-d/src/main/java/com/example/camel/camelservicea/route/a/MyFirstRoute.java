//package com.example.camel.camelservicea.route.a;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.nio.charset.StandardCharsets;
//import java.util.stream.Collectors;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.LoggingLevel;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MyFirstRoute extends RouteBuilder{
//
//	@Override
//	public void configure() throws Exception {
//		from("timer:firstRoute?period=4000")
//		  .routeId("firstTimerId")
//		  .setBody(constant("Hello World"))
//		  .log(LoggingLevel.INFO,"${body}");
//		
//		from("file:C:/shiv")
//		.log("reading file")
//		.process(new Processor() {
//			
//			@Override
//			public void process(Exchange exchange) throws Exception {
//				// TODO Auto-generated method stub
//				String code = exchange.getIn().getBody(String.class);
//				File file = new File("C:/code/NewCode.java");
//				FileWriter fileWriter = new FileWriter(file);
//				fileWriter.write(code);
//				fileWriter.close();
//				exchange.getMessage().setBody("C:/shiv/Code.java");
//			}
//		})
//		.to("direct:exec");
//		
////		from("direct:exec")
////		.log("Code came to direct:exec")
////		.to("exec:cmd?args=/C javac C:/code/NewCode.java")
////		.log("class file created")
////		.to("direct:run");
////		
////		from("direct:run")
////		.log("Code came to direct:run")
////		.to("exec:cmd?args=/C cd C://code&java NewCode")
//////		.to("exec:java?args= -f C://code/NewCode.class&outFile=C://code/CamelExecOutFile.txt")
////		//.to("exec:java?args=/C -f NewCode.class")
////		.process(new Processor() {
////		     public void process(Exchange exchange) throws Exception {
////		       // By default, the body is ExecResult instance
//////		       assertIsInstanceOf(ExecResult.class, exchange.getIn().getBody());
////		       
////		     //  String output = exchange.getIn().getBody(String.class);
////		       
////		       String text = new BufferedReader(
////		    		      new InputStreamReader(exchange.getIn().getBody(InputStream.class), StandardCharsets.UTF_8))
////		    		        .lines()
////		    		        .collect(Collectors.joining("\n"));
////		       // Use the Camel Exec String type converter to convert the ExecResult to String
////		       // In this case, the stdout is considered as output
////		       System.out.println("Output is \n===========================>>>>>>>>>>>>>>>>>>>>>>");
////		       System.out.println(text);
////		       System.out.println();
////		       // do something with the word count
////		     }
////		});
//		
//	}
//
//	
//}
