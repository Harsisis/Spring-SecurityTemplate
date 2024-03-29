package fr.spring.springSecuritydemo.config.security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtIssuer {
  
   @Value("${security.jwt}")
   private String SECRET_KEY;

   /**
    * Issue a JWT token using logged user informations
    * @param userId
    * @param email
    * @param enable
    * @param role
    * @return
    */
   public String issue(long userId, String email, boolean enable, List<String> role) {
      return JWT.create()
            .withSubject(String.valueOf(userId))
            .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
            .withClaim("email", email)
            .withClaim("enable", enable)
            .withClaim("role", role)
            .sign(Algorithm.HMAC256(SECRET_KEY));
   }
}
