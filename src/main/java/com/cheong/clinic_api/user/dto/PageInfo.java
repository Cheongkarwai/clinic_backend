package com.cheong.clinic_api.user.dto;

import com.cheong.clinic_api.auth.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageInfo {

	private boolean hasPrevious;
}
