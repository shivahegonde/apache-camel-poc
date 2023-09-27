package com.example.camel.camelservicea.model;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@XmlRootElement
@XmlType
@Entity(name="student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	private String name;
	private String address;
	private String age;
	
}
