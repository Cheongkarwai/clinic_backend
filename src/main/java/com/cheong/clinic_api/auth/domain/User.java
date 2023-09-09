package com.cheong.clinic_api.auth.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cheong.clinic_api.appointment.domain.Appointment;
import com.cheong.clinic_api.auth.input.UserInput;
import com.cheong.clinic_api.common.constant.DatabaseConstant;
import com.cheong.clinic_api.message.domain.ChatSession;
import com.cheong.clinic_api.message.dto.Message;
import com.cheong.clinic_api.order.domain.Order;
import com.cheong.clinic_api.user.domain.UserProfile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {

	@Id
	@Column(nullable = false, length = 50)
	private String id;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(name = "enabled", nullable = false)
	private Boolean isEnabled;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = DatabaseConstant.TBL_USER_AUTHORITIES, joinColumns = {
			@JoinColumn(name = DatabaseConstant.COL_USER_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DatabaseConstant.COL_AUTHORITY_ID) })
	private Set<Authority> authorities;

	@Builder.Default
	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "firstUser", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ChatSession> firstUserChatSessions = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "secondUser", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ChatSession> secondUserChatSessions = new ArrayList<>();
	
	@OneToMany(mappedBy = "sentBy")
	private List<Message> userSentMessages = new ArrayList<>();
	
	@OneToMany(mappedBy = "receivedBy")
	private List<Message> userReceiveMessage = new ArrayList<>();
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	private UserProfile userProfile;
	
	@OneToMany(mappedBy = "user")
	private List<Appointment> appointments = new ArrayList<>();

	public void addOrder(Order order) {
		order.setUser(this);
		orders.add(order);
	}

	public User(UserInput userDto) {
		this.id = userDto.getUsername();
		this.password = userDto.getPassword();
		this.isEnabled = false;
	}
}
