package com.nail.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailConfiguration userDetailConfiguration;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailConfiguration).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/clients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/clients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/clients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/clients/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/service/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/service/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/service/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/service/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
