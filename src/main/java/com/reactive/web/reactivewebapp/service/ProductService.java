package com.reactive.web.reactivewebapp.service;

import java.util.List;

import com.reactive.web.reactivewebapp.model.Product;

public interface ProductService {

	List<Product> findProductByDesc(String text);

	List<Product> findProductByPriceMoreThan50();

	List<Product> findProductByPriceMoreThan50WithPagination();

	Product updateProductDetails(Product product, String id);

}
