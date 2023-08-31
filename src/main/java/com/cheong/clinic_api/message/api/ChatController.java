package com.cheong.clinic_api.message.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheong.clinic_api.message.service.IMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
	
	private final IMessageService messageService;

	@GetMapping("/{sender}/recipients")
	public List<String> findAllChatBySender(@PathVariable String sender){
		return messageService.findAllChatBySender(sender);
	}
}
