package com.bill.tech.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.payload.request.PasswordHistoryDTO;
import com.bill.tech.service.ChangePasswordService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "ChangePasswordController", description = "This Section Gives Us The API Endpoint Related To The ChangePasswordController")
public class ChangePasswordController {
	@Autowired
	private ChangePasswordService changePasswordService;

	@GetMapping("/stay")
	String stay() {
		return "Hello World";
	}

	@PostMapping("verifyOtp/{userId}")
	public ResponseEntity<Map<String, Object>> verifyOtp(@PathVariable Integer userId) {
		return changePasswordService.verifyOtp(userId);
	}

//@GetMapping("getPasswordPolicy")
//public ResponseEntity<Map<String, Object>> getPasswordPolicy() {
//	return passwordService.getPasswordPolicy();
//}

	@PostMapping("changePassword/{userId}/{otp}")
	public ResponseEntity<Map<String, Object>> changePassword(@PathVariable Integer userId, @PathVariable Long otp,
			@Valid @RequestBody PasswordHistoryDTO optDto) {
		return changePasswordService.changePassword(userId, otp, optDto);
	}

	@PostMapping("check/{enc}/{raw}")
	public ResponseEntity<Map<String, Object>> checkBycryptPasswordIsSame(@PathVariable String enc,
			@PathVariable String raw) {
		return changePasswordService.checkBycryptPasswordIsSame(enc, raw);
	}
}
