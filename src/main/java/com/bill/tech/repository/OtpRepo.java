package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.OTP;

public interface OtpRepo extends JpaRepository<OTP, Integer> {
	OTP findByUserMasterId(int id);

	OTP findByUserMasterIdAndOtpNo(int userId, Long otpNo);

}
