package com.example.camel.camelservicea;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelServiceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelServiceAApplication.class, args);
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		connectionFactory.setUserName("admin");
		connectionFactory.setPassword("admin");
		
		return connectionFactory;
	}
	
	@Bean
	public JmsComponent jmss(ConnectionFactory connectionFactory) {
		JmsComponent jmsComponent = new JmsComponent();
		jmsComponent.setConnectionFactory(connectionFactory);
		return jmsComponent;
	}
	
}
