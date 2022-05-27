package com.example.dockerk8s.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping( "/hello" )
	public ResponseEntity< String > greetings() {

		return ResponseEntity.ok( "hello" );
	}
}
