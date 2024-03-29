package fr.spring.springSecuritydemo.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private static final String BEARER = "Bearer ";
   private static final String AUTHORIZATION = "Authorization";

   private final JwtDecoder jwtDecoder;
   private final JwtToPrincipalConverter jwtToPrincipalConverter;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
      extractTokenFromRequest(request)
         .map(jwtDecoder::decode)
         .map(jwtToPrincipalConverter::convert)
         .map(UserPrincipalAuthenticationToken::new)
         .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

      filterChain.doFilter(request, response);

   }

   /**
    * Trying to extract token from request header
    * 
    * @param request
    * @return token if exist or null otherwise
    */
   private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
      var token = request.getHeader(AUTHORIZATION);
      if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
         return Optional.of(token.substring(BEARER.length()));
      }
      return Optional.empty();
   }

}
