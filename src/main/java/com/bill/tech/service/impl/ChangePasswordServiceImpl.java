/**
 * 
 */
package com.bill.tech.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bill.tech.entity.OTP;
import com.bill.tech.entity.UserMaster;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.PasswordHistoryDTO;
import com.bill.tech.repository.OtpRepo;
import com.bill.tech.repository.UserMasterRepo;
import com.bill.tech.service.ChangePasswordService;
import com.bill.tech.util.EmailUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChangePasswordServiceImpl implements ChangePasswordService {

	public static final String MESSAGE = "MESSAGE";
	public static final String DATA = "Data";
	public static final String SUCCESS = "SUCCESS";

	@Autowired
	private EmailUtil mailUtil;

	@Autowired
	private UserMasterRepo userMaster;

	@Autowired
	private OtpRepo otpRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * @Value("${server.type}") private String serverType;
	 */

	@SuppressWarnings("null")
	@Override
	public ResponseEntity<Map<String, Object>> verifyOtp(Integer id) {
		log.info("Enter into verifyOtp method...{} ");
		Map<String, Object> responseMap = new HashMap<>();
		try {
			UserMaster existingUser = userMaster.findById(id)
					.orElseThrow(() -> new ResourceNotFound("User", "Id", id.toString()));
			if (Objects.nonNull(existingUser)) {
				String email = existingUser.getEmailId();
				Long otp = EmailUtil.genOTP();
				OTP exitedOTP = otpRepository.findByUserMasterId(existingUser.getId());
				if (exitedOTP == null) {
					OTP newOTP = new OTP();
					newOTP.setEmail(email);
					newOTP.setOtpNo(otp);
					newOTP.setUserMaster(existingUser);
					newOTP.setCreatedBy(existingUser.getId());
					otpRepository.save(newOTP);
//					responseMap.put(MESSAGE, "OTP added successfully...");
					log.info("OTP added successfully...");
				} else {
					exitedOTP.setEmail(email);
					exitedOTP.setOtpNo(otp);
					exitedOTP.setUserMaster(existingUser);
					exitedOTP.setUpdatedBy(existingUser.getId());
					exitedOTP.setUpdatedDate(LocalDateTime.now());
					otpRepository.save(exitedOTP);
//					responseMap.put(MESSAGE, "OTP updated successfully...");
					log.info("OTP updated successfully...");
				}
				String output = "";
				// if (serverType.equalsIgnoreCase("test")) {
				output = mailUtil.sendMailForOtp(id, email, otp);
				// }

				responseMap.put(MESSAGE, output);
				responseMap.put(SUCCESS, true);

				Map<String, String> dataMap = new HashMap<>();
				dataMap.put("email", email);
				dataMap.put("userId", id + "");
				responseMap.put("DATA", dataMap);
			} else {
				responseMap.put(SUCCESS, false);
				responseMap.put(DATA, "Employee not found with staffId " + existingUser.getId());
			}
		} catch (Exception e) {
			log.error("Error while  changePassword  :" + e);
			responseMap.put(SUCCESS, false);
			responseMap.put(MESSAGE, "Employee not found with user ID : " + id);
//			throw new EPSException(e);
		}
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@SuppressWarnings("null")
	@Override
	public ResponseEntity<Map<String, Object>> changePassword(Integer id, Long otp, PasswordHistoryDTO dto) {
		log.info("Enter into changePassword method...{} ");
		Map<String, Object> responseMap = new HashMap<>();
		UserMaster existingUser = userMaster.findById(id)
				.orElseThrow(() -> new ResourceNotFound("User", "Id", id.toString()));
		OTP checkOtp = otpRepository.findByUserMasterIdAndOtpNo(id, otp);
		if (Objects.isNull(checkOtp)) {
			responseMap.put(SUCCESS, false);
			responseMap.put(MESSAGE, "Please enter valid OTP.");
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		}
		if (!dto.getPassword().equals(dto.getConfirmPass())) {
			responseMap.put(SUCCESS, false);
			responseMap.put(MESSAGE, "Password and Confirm Password doesn't match!");
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		}

		if (existingUser.getOldPassword() != null) {
			List<String> oldPasStrings = existingUser.getOldPassword();
			oldPasStrings.add(existingUser.getPassword());

			for (String oldPass : oldPasStrings) {
				if (bCryptPasswordEncoder.matches(dto.getPassword(), oldPass)) {
					responseMap.put(SUCCESS, false);
					responseMap.put(MESSAGE, "New password must not be the same as previous passwords.");
					return new ResponseEntity<>(responseMap, HttpStatus.OK);
				}
			}
		}
		if (existingUser.getOldPassword() != null) {
			existingUser.getOldPassword().add(existingUser.getPassword());
		} else {
			existingUser.setOldPassword(List.of(existingUser.getPassword()));
		}
		existingUser.setPassword(bCryptPasswordEncoder.encode(dto.getConfirmPass()));
		userMaster.save(existingUser);
		responseMap.put(SUCCESS, true);
		responseMap.put(MESSAGE, "Password changed successfully.");
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

//	@Override
//	public ResponseEntity<Map<String, Object>> getPasswordPolicy() {
//		log.info("Enter into getPasswordPolicy method...{} ");
//		Map<String, Object> responseMap = new HashMap<>();
//		try {
//			PasswordPolicy policy = policyRepository.findTopByOrderByCreatedDateDesc();
//			if (Objects.nonNull(policy)) {
//				
//				responseMap.put(SUCCESS, true);
//				responseMap.put(DATA, TO_POLICY_DTO.apply(policy));
//			} else {
//				responseMap.put(SUCCESS, false);
//				responseMap.put(DATA, "Something went wrong to fetch Password Policy.");
//			}
//		} catch (Exception e) {
//			log.error("Error while changePassword  :" + e);
//			responseMap.put(SUCCESS, false);
//			throw new EPSException(e);
//		}
//		return new ResponseEntity<>(responseMap, HttpStatus.OK);
//	}

	@Override
	public ResponseEntity<Map<String, Object>> checkBycryptPasswordIsSame(String ecrypted, String rawpassword) {
		log.info("Enter into changePassword method...{} ");
		Map<String, Object> responseMap = new HashMap<>();

		if (bCryptPasswordEncoder.matches(rawpassword, ecrypted)) {
			responseMap.put(SUCCESS, true);
			responseMap.put(MESSAGE, "Same");
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		} else {
			responseMap.put(SUCCESS, false);
			responseMap.put(MESSAGE, "NOOO SAME!!");
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		}

	}
}
