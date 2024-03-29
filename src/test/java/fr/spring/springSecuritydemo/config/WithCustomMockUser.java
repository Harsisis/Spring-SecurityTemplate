package fr.spring.springSecuritydemo.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * Mock custom context with fake user for test purposes, default values :
 * <li>User id : 1</li>
 * <li>User role : 'ROLE_USER'</li>
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockedUserSecurityContextFactory.class)
public @interface WithCustomMockUser {
   long userId() default 1l;
   String[] authorities() default "ROLE_USER";
   String email() default "fake@email.com";

}
