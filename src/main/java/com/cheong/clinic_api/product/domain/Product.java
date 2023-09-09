package com.cheong.clinic_api.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.cheong.clinic_api.dto.ProductDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_product")
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator", 
      strategy = "com.cheong.clinic_api.product.domain.ProductIdGenerator")
	@Column(nullable = false)
	private String id;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@Column(nullable = false,precision = 14, scale = 2)
	private BigDecimal price;
	
	public Product(ProductDto productDto) {
		this.name = productDto.getName();
		this.description = productDto.getDescription();
		this.price = productDto.getPrice();
	}
}
