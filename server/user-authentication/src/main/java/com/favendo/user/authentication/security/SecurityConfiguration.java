package com.favendo.user.authentication.security;

import com.favendo.user.authentication.filter.AuthenticationFilter;
import com.favendo.user.authentication.utils.PathRequestMatcher;
import com.favendo.user.service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

import static com.favendo.commons.utils.Routes.*;
import static com.favendo.user.service.constant.RoleConstant.*;
import static com.favendo.user.service.constant.UserConstant.PASSWORD;
import static com.favendo.user.service.constant.UserConstant.USERNAME;
import static org.springframework.http.HttpMethod.OPTIONS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(OPTIONS, ALL_REQUEST).permitAll()
                .antMatchers(ADMIN_REQUEST).access(HAS_ADMIN_ROLE)
                .antMatchers(CUSTOMER_REQUEST).access(HAS_CUSTOMER_ROLE)
                .antMatchers(MERCHANT_REQUEST).access(HAS_MERCHANT_ROLE)
                .antMatchers(USER_INFO_REQUEST).access(HAS_ANY_ROLE)
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider)
                .userDetailsService(userDetailsService)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .formLogin()
                .loginProcessingUrl(LOGIN_REQUEST)
                .usernameParameter(USERNAME)
                .passwordParameter(PASSWORD)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler).permitAll()
                .and()
                .logout().permitAll()
                .and()
                .addFilterBefore(buildAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private AuthenticationFilter buildAuthTokenFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(FORM_BASED_LOGIN_ENTRY_POINT);
        PathRequestMatcher pathRequestMatcher = new PathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(this.userDetailsService, pathRequestMatcher);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }
}
