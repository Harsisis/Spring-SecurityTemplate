package fr.spring.springSecuritydemo.config.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Create custom handler for Unauthorized handling
 */
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {

   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
         throws IOException, ServletException {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
      
   }

}
