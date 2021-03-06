package com.reactive.web.reactivewebapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.reactive.web.reactivewebapp.model.Product;
import com.reactive.web.reactivewebapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public List<Product> findProductByDesc(String text) {
		TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
		Query query = TextQuery.queryText(textCriteria);
		return mongoOperations.find(query, Product.class);
	}

	@Override
	public List<Product> findProductByPriceMoreThan50() {
		Query query = Query.query(Criteria.where("price").gte(50.00))
				.with(Sort.by(Sort.Direction.ASC, "price"));
		return mongoOperations.find(query, Product.class);
	}

	
	@Override
	public List<Product> findProductByPriceMoreThan50WithPagination() {
		Query query = Query.query(Criteria.where("price").gte(50.00))
				.with(PageRequest.of(1, 20, Sort.by(Sort.Direction.ASC, "price")));
		return mongoOperations.find(query, Product.class);
	}

	@Override
	public Product updateProductDetails(Product product, String id) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update().set("name", product.getName()).set("price", product.getPrice());
		return mongoOperations.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true),
				Product.class);
	}
}
