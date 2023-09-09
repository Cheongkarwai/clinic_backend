package com.cheong.clinic_api.product.service;

import com.cheong.clinic_api.product.dto.ProductInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cheong.clinic_api.common.dto.TableCriteria;
import com.cheong.clinic_api.dto.ProductDto;
import com.cheong.clinic_api.order.dto.PageInput;
import com.cheong.clinic_api.product.domain.Product;
import com.cheong.clinic_api.product.dto.ProductPage;

import graphql.relay.Connection;

public interface IProductService {

	void createProduct(ProductDto productDto);

	
	Page<Product> findAll(Pageable pageable);
	
	Product findById(String id);
	
	ProductPage findAll(PageInput pageInput);

	Connection<Product> findAllProduct(PageInput pageInput);

	Connection<Product> findAllProduct(TableCriteria tableCriteria);


	String save(ProductInput productInput);

	Product update(String id,ProductInput productInput);

	void deleteById(String id);
}
