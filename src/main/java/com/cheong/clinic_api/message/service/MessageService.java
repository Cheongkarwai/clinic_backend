package com.cheong.clinic_api.message.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.message.domain.ChatSession;
import com.cheong.clinic_api.message.dto.ChatSessionDto;
import com.cheong.clinic_api.message.repository.ChatSessionRepository;
import com.cheong.clinic_api.user.service.IUserService;
import com.cheong.clinic_api.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService implements IMessageService {

	private final ChatSessionRepository chatSessionRepository;
	private final IUserService userService;
	private final ObjectMapper objectMapper;

	@Override
	public String findChatSessionId(String senderId, String recipientId) {

		Optional<ChatSession> chatSession = chatSessionRepository.findAllByFirstUserAndSecondUser(senderId,
				recipientId);

		if (chatSession.isEmpty()) {
			// create new chat session
			ChatSession savedChatSession = saveChatSession(
					ChatSessionDto.builder().senderId(senderId).recipientId(recipientId).build());

			return savedChatSession.getSessionId().toString();
		}

		return chatSession.get().getSessionId().toString();

	}

	@Override
	public ChatSession saveChatSession(ChatSessionDto chatSessionDto) {

		User sender = userService.loadByUsername(chatSessionDto.getSenderId());

		User recipient = userService.loadByUsername(chatSessionDto.getRecipientId());

		ChatSession chatSession = ChatSession.builder().firstUser(recipient).secondUser(sender).isActive(false).build();

		return chatSessionRepository.save(chatSession);
	}

	@Override
	public void saveChatSession(ChatSession chatSession) {
		chatSessionRepository.save(chatSession);
	}

	@Override
	public List<String> findAllChatBySender(String username) {
		
		List<ChatSession> chatSessions = chatSessionRepository.findAllByFirstUserIdOrSecondUserId(username, username);
		
		List<String> chatSessions1 = chatSessions.stream().flatMap(chat->Stream.of(chat.getFirstUser().getId(),chat.getSecondUser().getId()))
						.filter(chat->!chat.equals(username))
						.collect(Collectors.toList());
		
		chatSessions1.forEach(System.out::println);
		
		return chatSessions1;
	}

}
