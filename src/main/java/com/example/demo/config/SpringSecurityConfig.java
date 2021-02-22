package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	private final AccessDeniedHandler accessDeniedHandler;
	
	final DataSource dataSource;
	
	@Value("${spring.security.admin.username}")
	private String adminUsername;
	
	@Value("${spring.security.admin.username}")
	private String adminUserPassword;
	
	@Value("${spring.queries.users-query")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query")
	private String rolesQuery;
	
	@Autowired
	public SpringSecurityConfig(AccessDeniedHandler accessDeniedHandler, DataSource dataSource) {
		this.accessDeniedHandler = accessDeniedHandler;
		this.dataSource = dataSource;
	}
	
	
	
	
	
	
}
