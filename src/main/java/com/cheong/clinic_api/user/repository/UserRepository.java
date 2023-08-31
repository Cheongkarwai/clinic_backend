package com.cheong.clinic_api.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheong.clinic_api.auth.domain.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUserProfileEmail(String email);

	@Query("SELECT u FROM User u WHERE u.id > :cursor ORDER BY u.id ASC")
	List<User> findAllAfter(@Param("cursor") String after,Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.id < :cursor ORDER BY u.id ASC")
	List<User> findAllBefore(@Param("cursor") String after,Pageable pageable);
}
