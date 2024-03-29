package fr.spring.springSecuritydemo.config;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import fr.spring.springSecuritydemo.config.security.UserPrincipal;
import fr.spring.springSecuritydemo.config.security.UserPrincipalAuthenticationToken;

/**
 * Mock custom context with fake user for test purposes
 */
public class WithMockedUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

   @Override
   public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
      var authorities = Arrays.stream(annotation.authorities())
         .map(SimpleGrantedAuthority::new)
         .toList();

      var principal = UserPrincipal.builder()
         .email(annotation.email())
         .userId(annotation.userId())
         .authorities(authorities)
         .build();

      var context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
      return context;
   }

}
