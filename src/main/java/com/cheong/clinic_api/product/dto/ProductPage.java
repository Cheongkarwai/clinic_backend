package com.cheong.clinic_api.product.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cheong.clinic_api.common.dto.PageInfo;
import com.cheong.clinic_api.product.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPage implements Serializable{
	
	private List<Product> data;
	
	private PageInfo page;
	
	public ProductPage(Page<Product> page) {
		this.data = page.getContent();
		this.page = PageInfo.builder().totalElements(page.getTotalElements())
				.size(page.getSize())
				.page(page.getPageable().getPageNumber())
				.hasNext(page.hasNext())
				.hasPrev(page.hasPrevious())
				.totalPages(page.getTotalPages())
				.build();
	}
}
