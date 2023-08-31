package com.cheong.clinic_api.product.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cheong.clinic_api.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

	@Query("SELECT u FROM Product u WHERE u.id > :cursor ORDER BY u.id ASC")
	List<Product> findAllAfter(@Param("cursor")  String after, PageRequest ofSize);

	@Query("SELECT u FROM Product u WHERE u.id < :cursor ORDER BY u.id ASC")
	List<Product> findAllBefore(@Param("cursor") String before, PageRequest ofSize);

	@Query("SELECT u FROM Product u ORDER BY u.id ASC")
	List<Product> findAll(PageRequest ofSize);
}
