package com.example.FormService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
    private final String secretkey;

    public JwtTokenVerifier(@Value("${jwt.secret}") String secretkey) {
        this.secretkey = secretkey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpReq, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = httpReq.getHeader("Authorization");
        System.out.println("[JwtTokenVerifier] Authorization header: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[JwtTokenVerifier] No Bearer token found, skipping JWT verification.");
            chain.doFilter(httpReq, response);
            return;
        }
        String token = authHeader.replace("Bearer ", "");
        System.out.println("[JwtTokenVerifier] Token: " + token);
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretkey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("[JwtTokenVerifier] Claims: " + claims);
            String username = claims.getSubject();
            String role = (String) claims.get("role");
            String springRole = "ROLE_" + role;
            System.out.println("[JwtTokenVerifier] Username: " + username + ", Role: " + role + ", SpringRole: " + springRole);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singleton(() -> springRole)
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpReq));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JwtTokenVerifier] Authentication set in SecurityContext.");
        } catch (Exception e) {
            System.out.println("[JwtTokenVerifier] Exception: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }
        chain.doFilter(httpReq, response);
    }
}
