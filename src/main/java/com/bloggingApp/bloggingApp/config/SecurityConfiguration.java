package com.bloggingApp.bloggingApp.config;

import com.bloggingApp.bloggingApp.security.CustomUserDetailService;
import com.bloggingApp.bloggingApp.security.JwtAuthenticationEntryPoint;
import com.bloggingApp.bloggingApp.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final CustomUserDetailService customUserDetailService;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()

                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptions ->
                        exceptions
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                ;


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.customUserDetailService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }
}
