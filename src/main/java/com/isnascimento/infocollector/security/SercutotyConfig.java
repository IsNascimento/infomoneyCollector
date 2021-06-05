package com.isnascimento.infocollector.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@EnableWebSecurity
public class SercutotyConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        http.cors().configurationSource(request -> configureCors(new CorsConfiguration()))
                .and().csrf().disable();
    }

    private CorsConfiguration configureCors(CorsConfiguration cors) {
        cors.applyPermitDefaultValues();
        cors.setAllowedMethods(Arrays.asList("GET", "POST"));
        return cors;
    }
}
