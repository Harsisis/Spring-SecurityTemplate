package fr.spring.springSecuritydemo.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

/**
 * Main configuration class for SpringSecurity
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

   private final JwtAuthenticationFilter jwtAuthenticationFilter;
   private final CustomUserDetailService customUserDetailService;
   private final UnauthorizedHandler unauthorizedHandler;

   @Bean
   public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

   // @formatter:off
      http
         .csrf(csrf -> csrf.disable())
         .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .formLogin(formLogin -> formLogin.disable())
         .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
         .authorizeHttpRequests(request -> request
            .requestMatchers("/", "/auth/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())
         .logout(logout -> logout.logoutUrl("/auth/logout"));
   // @formatter:on

      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(customUserDetailService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return new ProviderManager(authProvider);
   }

   /**
    * CORS origins url add new cors here
    */
   private static final String[] CROSS_ORIGIN = {"https://localhost:8090", "http://localhost:8090", "*",};
   private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};

   /**
    * Configure CORS to only allow query from URL and specified methods
    * 
    * @return
    */
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(Arrays.asList(CROSS_ORIGIN));
      configuration.setAllowedMethods(Arrays.asList(ALLOWED_METHODS));
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }

}
