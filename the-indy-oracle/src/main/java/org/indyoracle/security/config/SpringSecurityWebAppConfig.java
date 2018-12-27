package org.indyoracle.security.config;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Authentication configuration. Mainly for setting up Stormpath.
 * 
 * @author Guy
 *
 */

@Configuration
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        	.apply(stormpath())
        	.and()
        	.authorizeRequests().antMatchers("/**").permitAll();
    }
}