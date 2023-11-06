package com.bloggingApp.bloggingApp.controllers;

import com.bloggingApp.bloggingApp.payloads.JwtAuthorizationRequest;
import com.bloggingApp.bloggingApp.payloads.JwtAuthorizationResponse;
import com.bloggingApp.bloggingApp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthorizationResponse> createToken(@RequestBody JwtAuthorizationRequest request) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
        try {
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);

        JwtAuthorizationResponse jwtAuthorizationResponse = new JwtAuthorizationResponse();
        jwtAuthorizationResponse.setToken(token);

        return new ResponseEntity<JwtAuthorizationResponse>(jwtAuthorizationResponse, HttpStatus.OK);
    }

}
