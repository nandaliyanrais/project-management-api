package com.group1.projectmanagementapi.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group1.projectmanagementapi.applicationuser.ApplicationUser;
import com.group1.projectmanagementapi.applicationuser.ApplicationUserRepository;
import com.group1.projectmanagementapi.authentication.jwt.JwtProvider;
import com.group1.projectmanagementapi.authentication.models.dto.JwtResponseDto;
import com.group1.projectmanagementapi.authentication.models.dto.LoginRequestDto;
import com.group1.projectmanagementapi.customer.models.Customer;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Login user")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ApplicationUserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        logger.info("Attempt login to system: {}", loginRequestDto);

        ApplicationUser applicationUser = userRepository.findByUsernameOrEmail(loginRequestDto.getUsernameOrEmail(), loginRequestDto.getUsernameOrEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), loginRequestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        
        Customer customer = applicationUser.getCustomer();

        JwtResponseDto jwtResponseDto = JwtResponseDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .token(jwt)
                .build();

        return ResponseEntity.ok(jwtResponseDto);
    }

}