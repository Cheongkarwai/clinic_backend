package com.cheong.clinic_api.message.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cheong.clinic_api.message.dto.ChatSessionDto;
import com.cheong.clinic_api.message.dto.Message;
import com.cheong.clinic_api.message.dto.MessageDto;
import com.cheong.clinic_api.message.service.IMessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

	private final SimpMessagingTemplate simpMessagingTemplate;
	private final IMessageService messageService;

	@MessageMapping("/hello/{recipient}")
	public void sendMessage(@Payload MessageDto message, SimpMessageHeaderAccessor simpMessageHeaderAccessor,
			@DestinationVariable String recipient) {

		String chatSessionId = messageService.findChatSessionId(message.getSenderId(), recipient);
		
		simpMessagingTemplate.convertAndSendToUser(chatSessionId,"/queue/messages", message);
	}

	@MessageMapping("/test")
	public void sendSecuredMessage(@Payload Message message) {

	}

	@GetMapping("/api/chat-sessions")
	@ResponseBody
	public HttpEntity<String> findChatSession(@RequestParam String senderId, @RequestParam String recipientId) {
		
		return ResponseEntity.ok(messageService.findChatSessionId(senderId, recipientId));
	}
}
