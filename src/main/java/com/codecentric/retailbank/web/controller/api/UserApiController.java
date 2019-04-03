package com.codecentric.retailbank.web.controller.api;

import com.codecentric.retailbank.exception.runtime.rest.ResourceNotFoundException;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.repository.security.UserRepository;
import com.codecentric.retailbank.security.CurrentUser;
import com.codecentric.retailbank.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.single(userPrincipal.getId());

        if (user == null)
            throw new ResourceNotFoundException("User", "id", userPrincipal.getId());

        return user;
    }
}
