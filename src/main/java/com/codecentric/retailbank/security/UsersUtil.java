package com.codecentric.retailbank.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsersUtil {

    private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

    public static boolean isAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(ROLE_ADMIN);
    }

    public static boolean isUser(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(ROLE_USER);
    }
}
