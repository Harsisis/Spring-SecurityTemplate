package fr.spring.springSecuritydemo.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * User entity, not mapped to database for test purposes
 */
@Getter
@Setter
@AllArgsConstructor
public class User {

   private Long id;
   private String email;
   @JsonIgnore
   private String password;
   private String firstName;
   private String lastName;
   private boolean enable;
   private String role;
}
