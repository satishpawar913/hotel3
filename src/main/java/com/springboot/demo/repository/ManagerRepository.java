package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.demo.entities.Manager;
import com.springboot.demo.entities.User;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{


}
