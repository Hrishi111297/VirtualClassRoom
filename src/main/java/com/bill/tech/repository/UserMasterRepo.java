package com.bill.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.UserMaster;



public interface UserMasterRepo  extends JpaRepository<UserMaster,Integer>{
	Optional<UserMaster> findByEmailId(String emailId);

}
