package com.codecentric.retailbank.security;

import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.singleByUsername(usernameOrEmail);
        if (user == null)
            throw new UsernameNotFoundException("User doesn't exists - Email: " + usernameOrEmail);

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.single(id);
        if (user == null)
            throw new UsernameNotFoundException("User doesn't exists - ID: " + id);

        return UserPrincipal.create(user);
    }
}
