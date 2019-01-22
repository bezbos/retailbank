package com.codecentric.retailbank.configuration;

import com.codecentric.retailbank.constants.Constant;
import com.codecentric.retailbank.security.CustomLoginSuccessHandler;
import com.codecentric.retailbank.security.CustomLogoutSuccessHandler;
import com.codecentric.retailbank.security.MyUserDetailsService;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan("com.codecentric.retailbank.security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;


    @Override protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/",

                        // Allowing anonymous access for testing purposes.
                        "/bank*",
                        "/bank/*",
                        "/address*",
                        "/address/*",
                        "/branch*",
                        "/branch/*",
                        "/customer*",
                        "/customer/*",
                        "/refTypes*",
                        "/refTypes/*",
                        "/bankAccount*",
                        "/bankAccount/*",
                        "/merchant*",
                        "/merchant/*",
                        "/transaction*",
                        "/transaction/*",
                        //

                        "/error",
                        "/home/*",
                        "/index",
                        "/shared/*",
                        "/css/*",
                        "/js/*").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/account/index",
                        "/account/login/oauth2/code/google*",
                        "/account/login/oauth2/code/google/*",
                        "/account/oauth2/authorization/google*",
                        "/account/oauth2/authorization/google/*",
                        "/account/oauth_login*",
                        "/account/oauth_login/*",
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
                .loginPage("/account/login")
                .successHandler(loginSuccessHandler())
                .permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
                .logoutSuccessUrl("/account/logout-success")
                .logoutSuccessHandler(logoutSuccessHandler()).permitAll()
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400).userDetailsService(userDetailsService)
                .and()
                .oauth2Login()
                .loginPage("/account/oauth_login");
    }


    @Bean public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(Constant.BCRYPT_HASH_CYCLES);
    }

    @Bean public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean public AuthenticationSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

}
