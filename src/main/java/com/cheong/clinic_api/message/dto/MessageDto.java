package com.cheong.clinic_api.message.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {

	private String senderId;
	
	private String recipientId;
	
	private String content;
	
	private LocalDateTime timestamp = LocalDateTime.now();
}
