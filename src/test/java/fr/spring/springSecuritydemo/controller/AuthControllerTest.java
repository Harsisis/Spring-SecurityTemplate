package fr.spring.springSecuritydemo.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import fr.spring.springSecuritydemo.data.model.User;
import fr.spring.springSecuritydemo.service.UserService;

/**
 * Integration tests for {@link AuthController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {

   @Mock
   private UserService userService;

   @Autowired
   private MockMvc api;

   /**
    * Mock data so even if the project is linked to a database, tests should works
    */
   @BeforeEach
   public void initMock() {
      doReturn(Optional.of(new User(1l, "mail1@test.com", "$2y$10$k5H6nlW4GAMi77f3dmiQFud6Bj/MWZ4D5eWEL536.6d/Jc/WemjKa", "Admin",
            "Enabled", true, "ROLE_ADMIN")))
         .when(userService)
         .findByEmail("mail1@test.com");
   }

   @Test
   public void call_login_should_return_200() throws Exception {
      api.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
         .content("""
                  {
                     "email" : "mail1@test.com",
                     "password" : "test"
                  }
               """))
         .andExpect(status().isOk());
   }

   @Test
   public void call_login_using_fake_credentials_should_return_401() throws Exception {
      api.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
         .content("""
                  {
                     "email" : "fake@test.com",
                     "password" : "fake"
                  }
               """))
         .andExpect(status().isUnauthorized());
   }

   @Test
   public void call_login_using_fake_password_should_return_401() throws Exception {
      api.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
         .content("""
                  {
                     "email" : "mail1@test.com",
                     "password" : "fake"
                  }
               """))
         .andExpect(status().isUnauthorized());
   }
   
   @Test
   public void call_logout_should_return_302() throws Exception {
      api.perform(post("/auth/logout"))
         .andExpect(status().isFound());
   }

}
