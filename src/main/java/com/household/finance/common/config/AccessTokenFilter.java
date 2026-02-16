package com.household.finance.common.config;

import com.household.finance.user.repository.UserRepository;
import com.household.finance.authentication.service.TokenService;
import com.household.finance.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getRequestToken(request);

        if (token != null) {
            String email = this.tokenService.verifyToken(token);
            User user = userRepository.findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(email).orElseThrow();

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getRequestToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);

        return authHeader != null ? authHeader.replace(BEARER, "") : null;
    }
}
