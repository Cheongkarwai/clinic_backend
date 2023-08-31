package com.cheong.clinic_api.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.hierarchicalroles.NullRoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomSidRetrievalStrategyImpl implements SidRetrievalStrategy {

	private RoleHierarchy roleHierarchy = new NullRoleHierarchy();

//	public SidRetrievalStrategyImpl(RoleHierarchy roleHierarchy) {
//		Assert.notNull(roleHierarchy, "RoleHierarchy must not be null");
//		this.roleHierarchy = roleHierarchy;
//	}

	@Override
	public List<Sid> getSids(Authentication authentication) {
		
		Jwt jwt = (Jwt) authentication.getPrincipal();
		
		Collection<? extends GrantedAuthority> authorities = jwt.getClaimAsStringList("jwt_acl").stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		
		List<Sid> sids = new ArrayList<>(authorities.size() + 1);
		sids.add(new PrincipalSid(authentication));
		for (GrantedAuthority authority : authorities) {
			sids.add(new GrantedAuthoritySid(authority));
		}
		return sids;
	}

}

