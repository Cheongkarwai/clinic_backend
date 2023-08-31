package com.cheong.clinic_api.message.service;

import java.util.List;

import com.cheong.clinic_api.message.domain.ChatSession;
import com.cheong.clinic_api.message.dto.ChatSessionDto;

public interface IMessageService {

	public String findChatSessionId(String sender, String recipient);
	
	public void saveChatSession(ChatSession chatSession);

	ChatSession saveChatSession(ChatSessionDto chatSessionDto);
	
	List<String> findAllChatBySender(String username);
}
