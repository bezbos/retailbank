package com.codecentric.retailbank.web.controller.api;

import com.codecentric.retailbank.exception.runtime.rest.AppException;
import com.codecentric.retailbank.model.security.Role;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.payload.ApiResponse;
import com.codecentric.retailbank.payload.JwtAuthenticationResponse;
import com.codecentric.retailbank.payload.LoginRequest;
import com.codecentric.retailbank.payload.SignUpRequest;
import com.codecentric.retailbank.repository.security.RoleRepository;
import com.codecentric.retailbank.repository.security.UserRepository;
import com.codecentric.retailbank.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        // We check if the email is already taken by someone.
        User userWithEmail = userRepository.singleByUsername(signUpRequest.getEmail());
        if (userWithEmail != null)
            return new ResponseEntity<>(new ApiResponse(false, "Email is already in use!"),
                    HttpStatus.BAD_REQUEST);

        // We create the user's account.
        User user = new User(signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.singleByName("ROLE_USER");
        if (userRole == null) throw new AppException("User Role not set.");
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.add(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/me")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully."));
    }
}
