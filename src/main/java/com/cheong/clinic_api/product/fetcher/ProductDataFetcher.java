package com.cheong.clinic_api.product.fetcher;

import org.springframework.cache.annotation.Cacheable;

import com.cheong.clinic_api.common.dto.TableCriteria;
import com.cheong.clinic_api.order.dto.PageInput;
import com.cheong.clinic_api.product.domain.Product;
import com.cheong.clinic_api.product.dto.ProductInput;
import com.cheong.clinic_api.product.dto.ProductPage;
import com.cheong.clinic_api.product.service.IProductService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import graphql.relay.Connection;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class ProductDataFetcher{

	private final IProductService productService;

	@DgsData(parentType="Query",field = "products")
	//@Cacheable(value="products",key="'product-page-'+#pageInput.page")
	public Connection<Product> findAll(@InputArgument TableCriteria tableCriteria) {
		return productService.findAllProduct(tableCriteria);
	}
	
	@DgsData(parentType="Mutation",field = "saveProduct")
	public String save(@InputArgument ProductInput productInput) {
		System.out.println("Hi");
		return "Hi";
	}
}
