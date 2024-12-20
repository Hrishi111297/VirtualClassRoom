package com.bill.tech.authentication;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.repository.UserMasterRepo;
import com.bill.tech.service.UserMasterService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class SecurityController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper helper;

	@Autowired
	UserMasterRepo userMasterRepo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserMasterService userMasterService;

	private Logger logger = LoggerFactory.getLogger(SecurityController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request, HttpServletResponse response)
			throws InterruptedException {
		this.doAuthenticate(request.getUsername(), request.getPassword());
		System.out.println(request.getUsername() + "&" + request.getPassword());
		// Thread.sleep(5000);

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.helper.generateToken(userDetails);

		// Cookie cookie = new Cookie("jwtToken", token);
		// cookie.setHttpOnly(true); // Ensures the cookie is only accessible via HTTP
		// (not accessible via
		// JavaScript)
		// cookie.setPath("/"); // Set the cookie path to root ("/") so it's accessible
		// from all paths
		// response.addCookie(cookie);
		JwtResponse jwtResponse = JwtResponse.builder().token(token).username(userDetails.getUsername())
				.userMasterDataRequestDto(modelMapper.map(userDetails, UserMasterDataRequestDto.class)).build();
		// JwtResponse jwtResponse = JwtResponse.builder().token(token).build();

		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
	}

	private void doAuthenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			authenticationManager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}

	@GetMapping("/status")
	public ResponseEntity<String> getStatus() {
		logger.info("Server is running");
		return new ResponseEntity<>("Server is running", HttpStatus.OK);

	}

	@PostMapping("/register")
	public ResponseEntity<UserMasterDataRequestDto> addUser(@Valid @RequestBody UserMasterDataRequestDto e) {
		return new ResponseEntity<>(this.userMasterService.addUser(e), HttpStatus.CREATED);
	}

}
