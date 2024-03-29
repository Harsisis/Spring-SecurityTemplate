package fr.spring.springSecuritydemo.data.struct;

/**
 * Record returned after successful login attempt
 */
public record LoginResponse(String accessToken) {

}
