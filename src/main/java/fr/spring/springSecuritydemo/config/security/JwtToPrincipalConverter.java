package fr.spring.springSecuritydemo.config.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Convert a {@link DecodedJWT} to {@link UserPrincipal}
 */
@Component
public class JwtToPrincipalConverter {
   
   public UserPrincipal convert(DecodedJWT jwt) {
      return UserPrincipal.builder()
         .userId(Long.valueOf(jwt.getSubject()))
         .email(jwt.getClaim("email").asString())
         .enable(jwt.getClaim("enable").asBoolean())
         .authorities(extractAuthoritiesFromClaim(jwt))
         .build();
   }
   
   private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
      var claim = jwt.getClaim("role");
      if(claim.isNull() || claim.isMissing()) {
         return List.of();
      }
      return claim.asList(SimpleGrantedAuthority.class);
   }
}
