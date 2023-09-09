package com.cheong.clinic_api.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.cheong.clinic_api.product.dto.ProductInput;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.common.dto.TableCriteria;
import com.cheong.clinic_api.dto.ProductDto;
import com.cheong.clinic_api.order.dto.PageInput;
import com.cheong.clinic_api.product.domain.Product;
import com.cheong.clinic_api.product.dto.ProductPage;
import com.cheong.clinic_api.product.repository.ProductRepository;

import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductService implements IProductService {

	private ProductRepository productRepository;

	@PersistenceContext
	private EntityManager em;
	private CriteriaBuilder cb;

	@PostConstruct
	public void init() {
		cb = em.getCriteriaBuilder();
	}

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void createProduct(ProductDto productDto) {
		productRepository.save(new Product(productDto));
	}

//	@Logging
	@Override
	public Page<Product> findAll(Pageable pageable) {

		productRepository.findAll(pageable).getContent().forEach(System.out::println);

		return productRepository.findAll(pageable);
	}

	@Cacheable(value = "product",key = "{#id}")
	@Override
	public Product findById(String id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("No product id " + id + " found"));
	}

	@Override
	public Connection<Product> findAllProduct(PageInput pageInput) {
		// return new
		// ProductPage(productRepository.findAll(PageRequest.of(pageInput.getPage(),pageInput.getSize())));
		return null;
	}

	@Override
	public ProductPage findAll(PageInput pageInput) {
		return null;
		// return new
		// ProductPage(productRepository.findAll(PageRequest.of(pageInput.getPage(),
		// pageInput.getSize())));
	}

	@Override
	public Connection<Product> findAllProduct(TableCriteria tableCriteria) {

		List<Edge<Product>> productEdges = null;
		PageInfo pageInfo = null;

		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> product = cq.from(Product.class);
		cq.select(product);
		
		Predicate searchPredicate = null;
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(StringUtils.hasText(tableCriteria.getSearch().getValue())){
			List<Predicate> searchPredicates = new ArrayList<>();
			
			tableCriteria.getSearch().getColumns().forEach(e->{
				searchPredicates.add(cb.like(product.get(e).as(String.class), "%"+ tableCriteria.getSearch().getValue() +"%"));
			});
			searchPredicate = cb.or(Arrays.stream(searchPredicates.toArray()).toArray(Predicate[]::new));
			
			predicates.add(searchPredicate);
		}
		
//		if (tableCriteria.getPage().getBefore() != null && tableCriteria.getPage().getFirst() == null && tableCriteria.getPage().getAfter() == null && tableCriteria.getPage().getLast() != null) {
//			if(StringUtils.hasText(tableCriteria.getSearch().getValue())){
//				List<Predicate> searchPredicates = new ArrayList<>();
//				
//				tableCriteria.getSearch().getColumns().forEach(e->{
//					searchPredicates.add(cb.like(product.get(e).as(String.class), "%"+ tableCriteria.getSearch().getValue() +"%"));
//				});
//				Predicate searchPredicate = cb.or(Arrays.stream(searchPredicates.toArray()).toArray(Predicate[]::new));
//				
//				cq.select(product).where(cb.and(searchPredicate,cb.lessThan(product.get("id"),tableCriteria.getPage().getBefore())));
//			}else {
//				cq.select(product).where(cb.lessThan(product.get("id"),tableCriteria.getPage().getBefore()));
//			}
//			
//			cq.orderBy(cb.asc(product.get("id")));
//			List<Product> products = em.createQuery(cq).setMaxResults(tableCriteria.getPage().getLast() + 1).getResultList();
//			
//			if(products.size() < tableCriteria.getPage().getLast() + 1) {
//				
//				productEdges = products.stream()
//						.map(e -> new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
//						.collect(Collectors.toList());
//				
//				pageInfo = new DefaultPageInfo(productEdges.get(0).getCursor(),
//						productEdges.get(products.size() - 1).getCursor(), products.size() > tableCriteria.getPage().getLast(), true);
//			}else {
//				productEdges = products.stream().skip(1)
//						.map(e -> new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
//						.collect(Collectors.toList());
//				
//				pageInfo = new DefaultPageInfo(productEdges.get(0).getCursor(),
//						productEdges.get(products.size() - 2).getCursor(), products.size() > tableCriteria.getPage().getLast(), true);
//			}
//
//			System.out.println(1);
//		}

		//if cursor is null and limit is not empty
		if (tableCriteria.getPage().getAfter() == null && tableCriteria.getPage().getFirst() != null && tableCriteria.getPage().getBefore() == null && tableCriteria.getPage().getLast() == null) {

			cq.where(cb.and(predicates.toArray(new Predicate[0])));
			cq.orderBy(cb.asc(product.get("id")));
			
			List<Product> products = em.createQuery(cq).setMaxResults(tableCriteria.getPage().getFirst() + 1).getResultList();
			
			if (products.size() > 0) {
				productEdges = products.stream().limit(tableCriteria.getPage().getFirst())
						.map(e -> new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
						.collect(Collectors.toList());

				pageInfo = new DefaultPageInfo(productEdges.get(0).getCursor(),
						productEdges.get(products.size() - 2).getCursor(), false, products.size() > tableCriteria.getPage().getFirst());
			}
			else {		
				productEdges = new ArrayList<>();
				pageInfo = new DefaultPageInfo(null,null, false, false);
			}
			
		} 
		//if cursor is not empty and limit is not empty
		else if (tableCriteria.getPage().getAfter() != null && tableCriteria.getPage().getFirst() != null && tableCriteria.getPage().getBefore() == null && tableCriteria.getPage().getLast() == null) {

			predicates.add(cb.greaterThan(product.get("id"), tableCriteria.getPage().getAfter()));
			
			cq.where(predicates.toArray(new Predicate[0]));
			cq.orderBy(cb.asc(product.get("id")));
			
			List<Product> products = em.createQuery(cq).setMaxResults(tableCriteria.getPage().getFirst() + 1).getResultList();
			
			productEdges = products.stream().limit(tableCriteria.getPage().getFirst())
					.map(e -> new DefaultEdge<>(e, new DefaultConnectionCursor(e.getId())))
					.collect(Collectors.toList());
			
			if(products.size() == tableCriteria.getPage().getFirst() + 1) {
				pageInfo = new DefaultPageInfo(productEdges.get(0).getCursor(),
						productEdges.get(products.size() - 2).getCursor(), true, products.size() > tableCriteria.getPage().getFirst());
			}else {
				pageInfo = new DefaultPageInfo(productEdges.get(0).getCursor(),
						productEdges.get(products.size() - 1).getCursor(), true, products.size() > tableCriteria.getPage().getFirst());
			}

		}

		return new DefaultConnection<>(productEdges, pageInfo);

	}
	
	@Override
	public String save(ProductInput productInput) {
		return productRepository.save(Product.builder().name(productInput.getName())
				.description(productInput.getDescription())
				.price(productInput.getPrice()).build()).getId();
	}

	@CachePut(key = "#id")
	@Override
	public Product update(String id,ProductInput productInput) {
		Product product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
		product.setName(productInput.getName());
		product.setPrice(productInput.getPrice());
		product.setDescription(productInput.getDescription());
		return productRepository.save(product);
	}

	@CacheEvict(key = "#id")
	@Override
	public void deleteById(String id) {
		productRepository.deleteById(id);
	}

}
