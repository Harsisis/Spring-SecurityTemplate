package fr.spring.springSecuritydemo.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fr.spring.springSecuritydemo.config.security.JwtIssuer;
import fr.spring.springSecuritydemo.config.security.UserPrincipal;
import fr.spring.springSecuritydemo.data.struct.LoginResponse;
import fr.spring.springSecuritydemo.data.struct.UserCredentials;
import lombok.RequiredArgsConstructor;

/**
 * Authentication service
 */
@Service
@RequiredArgsConstructor
public class AuthService {
   
   private final JwtIssuer jwtIssuer;
   private final AuthenticationManager authenticationManager;

   public LoginResponse attemptLogin(UserCredentials userCredentials) {
      var authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(userCredentials.email(), userCredentials.password()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
         UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
         
         var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
         
         return new LoginResponse(jwtIssuer.issue(principal.getUserId(), principal.getEmail(), principal.isEnable(), roles));
   }
}
