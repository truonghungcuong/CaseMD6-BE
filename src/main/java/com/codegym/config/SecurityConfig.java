package com.codegym.config;

import com.codegym.config.custom.CustomAccessDeniedHandler;
import com.codegym.config.custom.RestAuthenticationEntryPoint;
import com.codegym.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    IUserService userService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());//Tùy chỉnh lại thông báo 401 thông qua class restEntryPoint
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/image/**",
                        "/api/login",
                        "/api/register").permitAll()

//                .antMatchers("/api/returnTickets/**").authenticated()
//                .antMatchers("/api/borrowTickets/**").authenticated()
//                .antMatchers("/api/carts/**").authenticated()
//
//                .antMatchers(HttpMethod.POST, "/api/books/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_LIBRARIAN)
//                .antMatchers(HttpMethod.PUT, "/api/books/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_LIBRARIAN)
//                .antMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_LIBRARIAN)
//                .antMatchers(HttpMethod.GET, "/api/books/**").permitAll()
//
//                .antMatchers(HttpMethod.POST, "/api/categories/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_LIBRARIAN)
//                .antMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_LIBRARIAN)
//                .antMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyAuthority(Role.ROLE_ADMIN, Role.ROLE_LIBRARIAN)
//                .antMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
//
//                .antMatchers(HttpMethod.POST, "/api/manage_user/**").hasAuthority(Role.ROLE_ADMIN)
//
//                .antMatchers("/api/changePassword").authenticated()

        ;

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}
