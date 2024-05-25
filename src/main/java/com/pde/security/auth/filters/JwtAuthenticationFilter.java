package com.pde.security.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pde.business.service.auth.JwtService;
import com.pde.global.exceptions.BusinessException;
import com.pde.security.auth.ClientUserDetails;
import com.pde.security.auth.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

/**
 * @author abbas
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {
        extractJwtToken(request).ifPresentOrElse( // 1. extract and then look for the User with its JWT token.
                jwt -> {
                    String email = jwtUtil.getUserNameFromJwtToken(jwt);
                    try {
                        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) createAuthentication(email);
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        filterChain.doFilter(request, response);
                    } catch (BusinessException e) {
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }, () -> {
                    try {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "failed to authorize.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
        );
    }

    private Optional<String> extractJwtToken(HttpServletRequest request) {
        try {
            String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(auth)) {
                if (auth.startsWith(BEARER_PREFIX)) {
                    String token = auth.substring(BEARER_PREFIX.length()).trim();
                    if (!token.isBlank() && jwtUtil.validateTokens(token)) {
                        return Optional.of(token);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    private Authentication createAuthentication(String email) throws BusinessException {
        ClientUserDetails userDetails = (ClientUserDetails) userDetailsService.loadUserByUsername(email);
        jwtService.validateJwtTokenExpiration(userDetails.getId());
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        String servletPath = request.getServletPath();

        if (servletPath.contains("/dev"))
            return true;

        if (servletPath.contains("/swagger") || servletPath.contains("/v3/api-docs")) {
            return true;
        }

        if (servletPath.contains("/register") || servletPath.contains("/login")) {
            return true;
        }

        return false;
    }
}
