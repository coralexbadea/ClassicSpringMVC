package com.example.iticket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception{
        String loginPage = "/login";
        String logoutPage = "/logout";
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(loginPage).permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/index").hasAnyAuthority("ADMIN", "CASHIER")
                .antMatchers("/newPerformance").hasAnyAuthority("ADMIN")
                .antMatchers("/editPerformance/*").hasAnyAuthority("ADMIN")
                .antMatchers("/save").hasAnyAuthority("ADMIN", "CASHIER")
                .antMatchers("/deletePerformance/**").hasAnyAuthority("ADMIN")
                .antMatchers("/viewReservations").hasAnyAuthority("ADMIN", "CASHIER")
                .antMatchers("/downloadReservations").hasAnyAuthority("ADMIN")
                .antMatchers("/users").hasAnyAuthority("ADMIN", "CASHIER")
                .antMatchers("/editCashier/*").hasAnyAuthority("ADMIN")
                .antMatchers("/deleteCashier/**").hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage(loginPage)
                .loginPage("/")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/index")
                .usernameParameter("userName")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                .logoutSuccessUrl(loginPage)
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");


        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}
