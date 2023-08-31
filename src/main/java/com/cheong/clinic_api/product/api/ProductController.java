package com.cheong.clinic_api.product.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheong.clinic_api.assembler.ProductModelAssembler;
import com.cheong.clinic_api.dto.ProductDto;
import com.cheong.clinic_api.product.domain.Product;
import com.cheong.clinic_api.product.service.IProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private IProductService productService;
	
	private PagedResourcesAssembler<Product> pagedResourcesAssembler;
	
	private ProductModelAssembler productModelAssembler;
	
	public ProductController(IProductService productService,PagedResourcesAssembler<Product> pagedResourcesAssembler,ProductModelAssembler productModelAssembler) {
		this.productService = productService;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.productModelAssembler = productModelAssembler;
	}
	
	@GetMapping
	public HttpEntity<?> findAll(Pageable pageable){		
		return ResponseEntity.ok(pagedResourcesAssembler.toModel(productService.findAll(pageable), productModelAssembler));
	}
	
	@GetMapping("/{id}")
	public HttpEntity<?> findById(@PathVariable String id){
		
		return ResponseEntity.ok(productModelAssembler.toModel(productService.findById(id)));
	}

	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE})
	public HttpEntity<?> save(@RequestBody ProductDto productDto){
		productService.createProduct(productDto);
		
		return ResponseEntity.noContent().build();
	}
}
