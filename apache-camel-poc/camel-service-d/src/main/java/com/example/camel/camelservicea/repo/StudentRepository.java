package com.example.camel.camelservicea.repo;
import org.springframework.stereotype.Repository;

import com.example.camel.camelservicea.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{

}
