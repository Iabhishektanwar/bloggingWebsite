package com.bloggingApp.bloggingApp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;

        if(requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7);

            try {
                userName = jwtUtil.extractUsername(token);
            } catch (IllegalArgumentException e) {

            } catch (ExpiredJwtException e) {

            } catch (MalformedJwtException e) {

            }

        } else {

        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if(this.jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {

            }

        } else {

        }

        filterChain.doFilter(request, response);
    }
}
