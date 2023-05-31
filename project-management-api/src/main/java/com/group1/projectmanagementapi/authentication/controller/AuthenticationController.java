package com.group1.projectmanagementapi.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.applicationuser.ApplicationUserRepository;
import com.group1.projectmanagementapi.authentication.jwt.JwtProvider;
import com.group1.projectmanagementapi.authentication.models.UserPrincipal;
import com.group1.projectmanagementapi.authentication.models.dto.JwtResponseDto;
import com.group1.projectmanagementapi.authentication.models.dto.LoginRequestDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ApplicationUserRepository applicationUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        logger.info("Attempt login to system: {}", loginRequestDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        ApplicationUser applicationUser = applicationUserRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JwtResponseDto jwtResponseDto = JwtResponseDto.builder()
                .name(applicationUser.getName())
                .username(applicationUser.getUsername())
                .email(applicationUser.getEmail())
                .token(jwt)
                .build();

        return ResponseEntity.ok(jwtResponseDto);
    }

}