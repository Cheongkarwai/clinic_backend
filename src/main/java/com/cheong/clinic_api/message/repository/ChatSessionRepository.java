package com.cheong.clinic_api.message.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cheong.clinic_api.message.domain.ChatSession;

public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID>{

	@Query("SELECT u FROM ChatSession u WHERE (u.firstUser.id = :firstUser AND u.secondUser.id= :secondUser) OR "
			+ "(u.firstUser.id = :secondUser AND u.secondUser.id = :firstUser)")
	Optional<ChatSession> findAllByFirstUserAndSecondUser(
			String firstUser,String secondUser);

	List<ChatSession> findAllByFirstUserIdOrSecondUserId(String firstUser, String secondUser);

}
