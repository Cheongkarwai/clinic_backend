package com.cheong.clinic_api.order.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cheong.clinic_api.common.dto.PageInfo;
import com.cheong.clinic_api.order.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPage {

	private List<Order> data;
	
	private PageInfo page;
	
	public OrderPage(Page<Order> page) {
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
