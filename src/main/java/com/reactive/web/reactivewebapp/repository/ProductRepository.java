package com.reactive.web.reactivewebapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.web.reactivewebapp.model.Product;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{
	
	Flux<Product> findByName(String name);

}
