package com.cheong.clinic_api.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.cheong.clinic_api.auth.graphql.AuthGraphQL;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;

@SpringBootTest
public class AuthDataFetcherTest {

	@Autowired
	private DgsQueryExecutor dgsQueryExecutor;
	
	@Test
	public void testLoginUser() {
		


	}
}
