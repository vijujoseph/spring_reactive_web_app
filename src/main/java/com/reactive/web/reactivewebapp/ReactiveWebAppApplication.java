package com.reactive.web.reactivewebapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.reactive.web.reactivewebapp.model.Product;
import com.reactive.web.reactivewebapp.repository.ProductRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository repository) {
		return args -> {
			Flux<Product> prodFlux = Flux
					.just(new Product(null, "Samsung mobile", 100.99, "Product description Samsung mobile", "Product short title1"),
							new Product(null, "Apple Mac", 1000.99, "Product description Apple Mac", "Product short title2"),
							new Product(null, "Bose Speaker", 788.00, "Product description Bose Speaker", "Product short title3"),
							new Product(null, "Bose Speaker1", 30.5, "Product description Bose Speaker", "Product short title3"),
							new Product(null, "Bose Speaker2", 50.00, "Product description Bose Speaker", "Product short title3"))
					.flatMap(repository::save);

			prodFlux.thenMany(repository.findAll()).subscribe(System.out::println);
		};
	}

}
