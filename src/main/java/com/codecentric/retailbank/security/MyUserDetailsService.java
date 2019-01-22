package com.codecentric.retailbank.security;

import com.codecentric.retailbank.model.security.Role;
import com.codecentric.retailbank.model.security.User;
import com.codecentric.retailbank.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Helper flags
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // Handle user login
        try {
            // Check if incoming user exists
            User user = userRepository.singleByUsername(email);
            if (user == null)
                throw new UsernameNotFoundException("No user found with this username/email: " + email);

            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities((List<Role>) user.getRoles()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}
