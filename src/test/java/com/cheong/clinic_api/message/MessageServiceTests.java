package com.cheong.clinic_api.message;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.message.domain.ChatSession;
import com.cheong.clinic_api.message.dto.ChatSessionDto;
import com.cheong.clinic_api.message.repository.ChatSessionRepository;
import com.cheong.clinic_api.message.service.IMessageService;
import com.cheong.clinic_api.message.service.MessageService;
import com.cheong.clinic_api.user.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class MessageServiceTests {

	/*
	 * @MockBean allows us to specify the bean to be mocked and also
	 * using @Autowired will ensure that regular beans and mocked object will be
	 * injected
	 * 
	 */

	// @Mock
	@MockBean
	private ChatSessionRepository chatSessionRepository;

	// @InjectMocks
	@Autowired
	private IMessageService messageService;

	private String recipientId;

	private String senderId;

	private String chatSessionId;

	@BeforeEach
	public void setupBeforeEach() {
		this.recipientId = "020828140565";
		this.senderId = "020828140563";
		this.chatSessionId = "21c43394-18a9-11ee-be56-0242ac120002";
	}

	@Test
	public void assertSessionIdExistsByRecipientIdAndSenderId() {
		when(chatSessionRepository.findAllByFirstUserAndSecondUser(senderId, recipientId))
			.thenReturn(Optional.of(ChatSession.builder()
						.firstUser(User.builder().id(senderId).password(null).build())
						.secondUser(User.builder().id(recipientId).password(null).build())
						.sessionId(UUID.fromString(chatSessionId))
						.build()));
		
		assertEquals(messageService.findChatSessionId(senderId, recipientId),chatSessionId);
		
		verify(chatSessionRepository,times(1)).findAllByFirstUserAndSecondUser(senderId,recipientId);
	}

	@Test
	public void assertMessageServiceFindChatSessionIdIsNotNull() {
		
		when(chatSessionRepository.findAllByFirstUserAndSecondUser(senderId, recipientId))
		.thenReturn(Optional.of(ChatSession.builder()
					.firstUser(User.builder().id(senderId).password(null).build())
					.secondUser(User.builder().id(recipientId).password(null).build())
					.sessionId(UUID.fromString(chatSessionId))
					.build()));
		
		assertNotNull(messageService.findChatSessionId(senderId, recipientId));
	}

	@Test
	public void assertMessageServiceIsNotNull() {
		assertNotNull(messageService);
	}

	@Test
	public void throwChatSessionNotFound() {
		
		when(chatSessionRepository.findAllByFirstUserAndSecondUser("123","123"))
			.thenThrow(new NoSuchElementException("Chat session not found"));
		
		assertThrows(NoSuchElementException.class,()->messageService.findChatSessionId("123", "123"));
	}

	@Test
	public void throwSenderOrRecipientNotFound() {
		assertThrows(NoSuchElementException.class, () -> messageService
				.saveChatSession(ChatSessionDto.builder().senderId("zzz").recipientId("zzz").build()));
	}
}
