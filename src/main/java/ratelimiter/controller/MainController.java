package ratelimiter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ratelimiter.configuration.URIConstants;

@RestController
public class MainController {
	static int request = 0;

	@GetMapping(value = URIConstants.CONTROLLER_URI)
	public ResponseEntity<Object> getDetails() {
		System.out.println("request completed: " + ++request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
