package fr.spring.springSecuritydemo.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
   private static final long serialVersionUID = 1L;
   
   private final UserPrincipal userPrincipal;

   public UserPrincipalAuthenticationToken(UserPrincipal userPrincipal) {
      super(userPrincipal.getAuthorities());
      this.userPrincipal = userPrincipal;
      setAuthenticated(true);
      
   }

   @Override
   public String getCredentials() {
      return userPrincipal.getPassword();
   }

   @Override
   public UserPrincipal getPrincipal() {
      return userPrincipal;
   }

}
