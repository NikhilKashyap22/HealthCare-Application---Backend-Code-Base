package com.tg.Doctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
 
/**
* Configuration class for Spring Security.
* Sets up authentication, authorization, and password encoding mechanisms.
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {
 
    /**
     * Bean definition for the password encoder.
     * Uses BCrypt for hashing passwords.
     * 
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    /**
     * Bean definition for an in-memory user details manager.
     * Defines two users (admin and user) with roles ADMIN and USER respectively.
     * 
     * @return InMemoryUserDetailsManager instance with predefined users
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123")) // Encode the admin password
                .roles("ADMIN") // Assign ADMIN role to this user
                .build();
 
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user123")) // Encode the user password
                .roles("USER") // Assign USER role to this user
                .build();
 
        return new InMemoryUserDetailsManager(admin, user);
    }
 
    /**
     * Bean definition for the security filter chain.
     * Configures HTTP security settings, including endpoint access control.
     * 
     * @param http HttpSecurity object to configure security settings
     * @return SecurityFilterChain instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity; enable in production for security
            .authorizeRequests()
                .requestMatchers("/error").permitAll() // Allow unrestricted access to the error page
                .requestMatchers("/doctors/**").hasRole("ADMIN") // Restrict /doctors/** to ADMIN role
                .anyRequest().authenticated() // Require authentication for all other endpoints
            .and()
            .httpBasic(); // Use HTTP Basic authentication for simplicity
 
        return http.build();
    }
 
    /**
     * Bean definition for the authentication manager.
     * Delegates the authentication manager creation to the provided configuration.
     * 
     * @param authenticationConfiguration Configuration object for authentication
     * @return AuthenticationManager instance
     * @throws Exception if an error occurs during initialization
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}