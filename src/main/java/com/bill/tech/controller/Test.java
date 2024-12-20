package com.bill.tech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Test {

	@GetMapping("/trial")
	public String trial() {
		System.out.println("Helloascsansa");
		return "Hello World";
	}

}
