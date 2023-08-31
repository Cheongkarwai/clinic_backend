package com.cheong.clinic_api.message.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.common.constant.DatabaseConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name=DatabaseConstant.TBL_CHAT_SESSION)
public class ChatSession {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = DatabaseConstant.COL_SESSION_ID, unique = true)
	private UUID sessionId;

	@ManyToOne
	@JoinColumn(name=DatabaseConstant.COL_FIRST_USER)
	private User firstUser;

	@ManyToOne
	@JoinColumn(name=DatabaseConstant.COL_SECOND_USER)
	private User secondUser;
	
	@Column(name=DatabaseConstant.COL_ACTIVE)
	private boolean isActive;
}
