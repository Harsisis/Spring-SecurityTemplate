package fr.spring.springSecuritydemo.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtDecoder {

   @Value("${security.jwt}")
   private String SECRET_KEY;

   /**
    * Decode a JWT token
    * @param token
    * @return
    */
   public DecodedJWT decode(String token) {
      return JWT.require(Algorithm.HMAC256(SECRET_KEY))
         .withClaim("enable", true) // Force claim "enable" to be true -> otherwise return 403
         .build()
         .verify(token);
   }
}
