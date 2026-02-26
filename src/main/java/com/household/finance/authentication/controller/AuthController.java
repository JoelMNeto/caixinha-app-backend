package com.household.finance.authentication.controller;

import com.household.finance.authentication.dto.LoginData;
import com.household.finance.authentication.dto.RefreshTokenData;
import com.household.finance.authentication.dto.TokenDto;
import com.household.finance.authentication.service.TokenService;
import com.household.finance.user.service.UserService;
import com.household.finance.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginData loginData) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginData.email(), loginData.password());

        var authentication = this.authenticationManager.authenticate(authenticationToken);

        String token = tokenService.generateToken((User) authentication.getPrincipal());
        String refreshToken = tokenService.generateRefreshToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDto(token, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@Valid @RequestBody RefreshTokenData refreshTokenData) {
        Long id = Long.valueOf(this.tokenService.verifyToken(refreshTokenData.refreshToken()));

        var user = this.userService.findById(id);

        String token = tokenService.generateToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        return ResponseEntity.ok(new TokenDto(token, refreshToken));
    }
}
