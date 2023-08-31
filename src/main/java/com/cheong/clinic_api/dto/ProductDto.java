package com.cheong.clinic_api.dto;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.cheong.clinic_api.product.domain.Product;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Relation(collectionRelation = "products")
public class ProductDto extends RepresentationModel<ProductDto>{
	
	private String id;
	
	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}
}
