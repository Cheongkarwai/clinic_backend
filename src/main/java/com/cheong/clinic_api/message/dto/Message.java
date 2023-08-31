package com.cheong.clinic_api.message.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.common.constant.DatabaseConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DatabaseConstant.TBL_MESSAGE)
public class Message {

	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "sent_by")
	private User sentBy;
	
	@ManyToOne
	@JoinColumn(name = "received_by")
	private User receivedBy;
	
	private LocalDateTime timestamp;

}
