package com.app.interceptors;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.app.service.AccessTokenService;

public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("GOT REQUEST: {}", request.getRequestURI());
		String accessToken = request.getHeader("Authorization");
		if(accessToken != null && accessToken.length() >= 0) {
			try {
				boolean authorized = this.accessTokenService.verifyToken(accessToken);
				if(authorized) {
					return true;
				}
			} catch (Exception e) {
				logger.error("Error Verifying token: {}", e);
			}
		}
		
		logger.info("Unauthorized request {} {}", LocalDateTime.now(), request.getRequestURI());
		sendErrorResponse(response);
		return false;
	}
	
	private void sendErrorResponse(HttpServletResponse response) throws IOException {
		response.sendError(401, "Unauthorized");
	}
}
