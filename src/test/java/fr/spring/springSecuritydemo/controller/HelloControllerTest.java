package fr.spring.springSecuritydemo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import fr.spring.springSecuritydemo.config.WithCustomMockUser;

/**
 * Integration tests for {@link HelloController}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class HelloControllerTest {

   @Autowired
   private MockMvc api;

   @Test
   public void call_non_protected_endpoint_without_logged_user_should_return_200_and_expected_content() throws Exception {
      api.perform(get("/"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsStringIgnoringCase("Hello non-logged user")));
   }
   
   @Test
   public void call_protected_endpoint_without_logged_user_should_return_401() throws Exception{
      api.perform(get("/secured"))
      .andExpect(status().isUnauthorized());
   }
   
   @Test
   public void call_inexistant_endpoint_without_logged_user_should_return_401() throws Exception{
      api.perform(get("/inexistant"))
      .andExpect(status().isUnauthorized());
   }
   
   @Test
   @WithCustomMockUser
   public void call_inexistant_endpoint_with_logged_user_should_return_404() throws Exception{
      api.perform(get("/inexistant"))
      .andExpect(status().isNotFound());
   }
   
   @Test
   @WithCustomMockUser
   public void call_protected_endpoint_with_logged_user_should_return_200_and_contains_expected_content() throws Exception{
      api.perform(get("/secured"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsStringIgnoringCase("logged in as")));
   }
   
   @Test
   @WithCustomMockUser()
   public void call_role_protected_endpoint_with_logged_user_role_user_should_return_403() throws Exception{
      api.perform(get("/admin"))
      .andExpect(status().isForbidden());
   }
   
   @Test
   @WithCustomMockUser(authorities="ROLE_ADMIN")
   public void call_role_protected_endpoint_with_logged_user_role_admin_should_return_200_and_contains_expected_content() throws Exception{
      api.perform(get("/admin"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsStringIgnoringCase("hello admin")));
   }

}
