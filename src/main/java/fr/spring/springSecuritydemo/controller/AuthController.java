package fr.spring.springSecuritydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.spring.springSecuritydemo.data.struct.LoginResponse;
import fr.spring.springSecuritydemo.data.struct.UserCredentials;
import fr.spring.springSecuritydemo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;

/**
 * Controller to expose Authentication route(s)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

   private final AuthService authService;

   @PostMapping("/login")
   public ResponseEntity<LoginResponse> login(@RequestBody @Validated UserCredentials userCredentials) {
      return new ResponseEntity<>(authService.attemptLogin(userCredentials), HttpStatus.OK);
   }
}
