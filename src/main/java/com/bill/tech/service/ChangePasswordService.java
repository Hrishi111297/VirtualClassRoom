/**
 * 
 */
package com.bill.tech.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.bill.tech.payload.request.PasswordHistoryDTO;

public interface ChangePasswordService {

	ResponseEntity<Map<String, Object>> verifyOtp(Integer userId);

	ResponseEntity<Map<String, Object>> changePassword(Integer userId, Long otp, PasswordHistoryDTO optDto);

	// ResponseEntity<Map<String, Object>> getPasswordPolicy();

	// ResponseEntity<Map<String, Object>> verifyOtp(Integer id);
	ResponseEntity<Map<String, Object>> checkBycryptPasswordIsSame(String ecrypted, String rawpassword);

}
