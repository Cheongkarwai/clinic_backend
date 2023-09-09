package com.cheong.clinic_api.user.domain;

import jakarta.persistence.*;

import org.springframework.dao.DataAccessException;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.common.constant.DatabaseConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name=DatabaseConstant.TBL_USER_PROFILE)
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name=DatabaseConstant.COL_FULL_NAME)
	private String fullName;
	
	private String email;
	
	@Column(name=DatabaseConstant.COL_PHONE_NUMBER)
	private String phoneNumber;
	
	@Column(name=DatabaseConstant.COL_ADDRESS_LINE_1)
	private String addressLine1;
	
	@Column(name=DatabaseConstant.COL_ADDRESS_LINE_2)
	private String addressLine2;
	
	private String city;
	
	private String zipcode;
	
	@OneToOne
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private User user;
	
}
