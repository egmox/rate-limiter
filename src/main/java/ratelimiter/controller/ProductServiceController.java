package ratelimiter.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ratelimiter.dao.Product;

@RestController
public class ProductServiceController {
	private static Map<String, Product> productRepo = new HashMap<>();
	static int request = 0;

	
	CompletableFuture<Boolean> future = new CompletableFuture<>();
	
	@GetMapping(value = "/products")
	public ResponseEntity<Object> getProduct() throws Exception {
		System.out.println("request completed: " + ++request);
		return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
	}
}