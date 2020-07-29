package com.trmsys.finequal;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.trmsys.fabric.chassis.security.ChassisSecurityConfigurerAdapter;

@Configuration
@Order(99)
public class SecurityConfiguration extends ChassisSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.cors().and().csrf().disable();
    }
}
