package com.opopto.deploy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Value("${ldap.host.url}")
//    private String ldapHost;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // root for system init
        auth.inMemoryAuthentication().withUser("admin").password("Opopto@999").roles("ADMIN");

//        auth.ldapAuthentication()
//                .userSearchBase("ou=People")
//                .userSearchFilter("(uid={0})")
//                .contextSource()
//                .url(ldapHost);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            //403 forbidden
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/static/**","favicon.ico","/css/**","/js/**","/bootstrap/**","/keepalived").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .permitAll()
                .and();
    }
}