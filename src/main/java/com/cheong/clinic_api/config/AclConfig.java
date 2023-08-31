package com.cheong.clinic_api.config;

import javax.sql.DataSource;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.cheong.clinic_api.auth.CustomAclPermissionEvaluator;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AclConfig{
	
	private final DataSource dataSource;
	private final CacheManager cacheManager;
	
	@Bean
	public AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	@Bean
	public PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}
	
	@Bean
	public SpringCacheBasedAclCache aclCache() {
	    return new SpringCacheBasedAclCache(
	      cacheManager.getCache("aclCache"), 
	      permissionGrantingStrategy(), 
	      aclAuthorizationStrategy()
	    );
	}

	@Bean 
	public LookupStrategy lookupStrategy() { 
	    BasicLookupStrategy lookupStrategy = new BasicLookupStrategy(
	      dataSource, 
	      aclCache(), 
	      aclAuthorizationStrategy(), 
	      new ConsoleAuditLogger()
	    ); 
	    lookupStrategy.setAclClassIdSupported(true);
	    return lookupStrategy;
	}
	
	@Bean 
	public JdbcMutableAclService aclService() { 
	    JdbcMutableAclService jdbcMutableAclService = new JdbcMutableAclService(
	      dataSource, lookupStrategy(), aclCache()); 
	    jdbcMutableAclService.setAclClassIdSupported(true);
	    jdbcMutableAclService.setClassIdentityQuery("select currval(pg_get_serial_sequence('acl_class', 'id'))");
	    jdbcMutableAclService.setSidIdentityQuery("select currval(pg_get_serial_sequence('acl_sid', 'id'))");
	    return jdbcMutableAclService;
	}
	
	@Bean
	public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler
	      = new DefaultMethodSecurityExpressionHandler();
		CustomAclPermissionEvaluator aclPermissionEvaluator = new CustomAclPermissionEvaluator(aclService());
		expressionHandler.setPermissionEvaluator(aclPermissionEvaluator);
		return expressionHandler;
		
	}
}

