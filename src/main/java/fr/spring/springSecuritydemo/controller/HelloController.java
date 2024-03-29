package fr.spring.springSecuritydemo.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.spring.springSecuritydemo.config.security.UserPrincipal;
import fr.spring.springSecuritydemo.data.model.User;
import fr.spring.springSecuritydemo.service.UserService;
import lombok.RequiredArgsConstructor;

/**
 * Controller to expose test route(s)
 */
@RestController
@RequiredArgsConstructor
public class HelloController {
   
   private final UserService userService;
   
   /**
    * Non protected route
    * 
    * @return
    */
   @GetMapping("/")
   public String getHello() {
      return "Hello non-logged user";
   }

   /**
    * Protected route
    * 
    * @return
    */
   @GetMapping("/secured")
   public String getSecured(@AuthenticationPrincipal UserPrincipal principal) {
      Optional<User> user = userService.findById(principal.getUserId());
      return "logged in as " + user.get().getFirstName() + " " + user.get().getLastName() + ", with userID " + user.get().getId();
   }
   
   /**
    * Protected route, only  accessible for user with role 'role_admin'
    * 
    * @return
    */
   @GetMapping("/admin")
   public String getAdmin(@AuthenticationPrincipal UserPrincipal principal) {
      Optional<User> user = userService.findById(principal.getUserId());
      return "hello admin " + user.get().getFirstName() + " " + user.get().getLastName() + ", with role : " + user.get().getRole() + " and userID " + user.get().getId();
   }
}
