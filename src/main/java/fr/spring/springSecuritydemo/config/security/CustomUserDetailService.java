package fr.spring.springSecuritydemo.config.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.spring.springSecuritydemo.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

   private final UserService userService;

   @Override
   public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
      var user = userService.findByEmail(username)
         .orElseThrow();

      return UserPrincipal.builder()
         .email(user.getEmail())
         .enable(user.isEnable())
         .userId(user.getId())
         .password(user.getPassword())
         .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
         .build();
   }

}
