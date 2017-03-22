/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security
 *
 * @author Michael Young
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    // Simulated DB with 3 users
    private static final User[] USERS = new User[]{
        new User("abc", "qwerty"),
        new User("def", "asdfgh"),
        new User("ghi", "zxcvbn")
    };

    /**
     * configure - in memory authentication with users defined above
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> imudmc = authenticationManagerBuilder.inMemoryAuthentication();
        for (User user : USERS) {
            imudmc.withUser(user.getLogin()).password(user.getPassword()).roles("USER");
        }
    }

    /**
     * configure - request to buy an item (POST /item/**) requires authentication as one of the users defined above; any other request can be anonymous
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/item/**").hasRole("USER")
                .antMatchers("/**").anonymous()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }
}
