package com.cheong.clinic_api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.dto.ProductDto;
import com.cheong.clinic_api.order.api.OrderController;
import com.cheong.clinic_api.product.api.ProductController;
import com.cheong.clinic_api.product.domain.Product;
import com.cheong.clinic_api.user.api.UserController;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductDto>{

	public ProductModelAssembler() {
		super(ProductController.class, ProductDto.class);
	}

	@Override
	public ProductDto toModel(Product product) {
		
		ProductDto productDto = new ProductDto(product);
		
		
		//userModel.add(linkTo(methodOn(UserController.class).findByUsername(user.getUsername())).withSelfRel().withType("GET"));
		//userModel.add(linkTo(methodOn(OrderController.class).findAllByUsername(user.getUsername())).withRel("orders"));
		
		return productDto;
	}
	
	@Override
	public CollectionModel<ProductDto> toCollectionModel(Iterable<? extends Product> products){
		
		CollectionModel<ProductDto> collectionModel = super.toCollectionModel(products);
		
		//collectionModel.add(linkTo(methodOn(UserController.class).findAll(0, 0)).withSelfRel());
		
		return collectionModel;
		
	}
}