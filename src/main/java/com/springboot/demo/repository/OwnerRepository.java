package com.springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.entities.Rooms;

@Repository
public interface OwnerRepository extends JpaRepository<Rooms, Integer>{
	
	

}
