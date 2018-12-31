package com.codecentric.retailbank.spring;

import com.codecentric.retailbank.security.MyUserDetailsService;
import com.codecentric.retailbank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan("com.codecentric.retailbank.security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    MyUserDetailsService userDetailsService;

    public SecurityConfig() {
        super();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/home/*",
                        "/index",
                        "/shared/*",
                        "/css/*",
                        "/js/*").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/account/index",
                        "/account/login",
                        "/account/perform_login",
                        "/account/registration",
                        "/account/forgotPassword",
                        "/account/changePassword",
                        "/account/resetPassword",
                        "/account/registrationConfirm").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/account/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .authorizeRequests().antMatchers("/account/updatePassword*",
                "/account/savePassword*",
                "/updatePassword*")
                .hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .and()
                .formLogin()
                .loginPage("/account/login").permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
                .logoutSuccessUrl("/account/logout-success").permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
