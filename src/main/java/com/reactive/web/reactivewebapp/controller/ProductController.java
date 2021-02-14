package com.reactive.web.reactivewebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.web.reactivewebapp.model.Product;
import com.reactive.web.reactivewebapp.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {


	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping(path = "/all")
	public Flux<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	
	@GetMapping("{id}")
	public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
		return productRepository.findById(id)
				.map(product -> ResponseEntity.ok(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> saveProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	
	@PutMapping("{id}")
	public Mono<ResponseEntity<Product>> saveProduct(@PathVariable String id, 
			@RequestBody Product product) {
		return productRepository.findById(id)
				.flatMap(existingProd -> {
					existingProd.setName(product.getName());
					existingProd.setPrice(product.getPrice());
					return productRepository.save(existingProd);
				})
				.map(updatedProd -> ResponseEntity.ok(updatedProd))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> removeProduct(@PathVariable String id) {
		return productRepository.deleteById(id)
				.map(product -> ResponseEntity.ok(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping("/delete")
	public Mono<ResponseEntity<Void>> removeProducts() {
		return productRepository.deleteAll()
				.map(product -> ResponseEntity.ok(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
