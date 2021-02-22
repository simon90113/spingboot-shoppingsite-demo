package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import org.springframework.stereotype.Component;

//	When client have 403 by access denied
//

@Component
public class DoAccessDeniedHandler implements AccessDeniedHandler{
	
	private static Logger logger = LoggerFactory.getLogger(DoAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException e) throws IOException, ServletException {
		
		Authentication auth = 
				SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			logger.info(String.format("Client '%s' attempted to access the protected Page: %s", 
										auth.getName(), request.getRequestURI())); // URI? URL?
		}
		
		response.sendRedirect(request.getContextPath() + "/403");
	}
	
	
}
