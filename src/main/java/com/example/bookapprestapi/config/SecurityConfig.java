package com.example.bookapprestapi.config;

import com.example.bookapprestapi.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private ReaderService readerService;
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    public void setReaderService(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Autowired
    public void setJwtTokenFilter(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https) throws Exception {
        https
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req) -> req
                        .requestMatchers(
                                "/auth",
                                "/reg",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers(
                                "/books/all",
                                "/books/{id}",
                                "/books/by/category/{categoryId}",
                                "/books/by/views",
                                "/books/by/downloads",
                                "/books/cheapest",
                                "/books/expensive",
                                "/books/add/comment/{id}",
                                "/favorites/all/{readerId}",
                                "/favorites/add/favorite",
                                "/favorites/delete").authenticated()
                        .requestMatchers(
                                "/books/all",
                                "/books/{id}",
                                "/books/by/category/{categoryId}",
                                "/books/by/views",
                                "/books/by/downloads",
                                "/books/cheapest",
                                "/books/expensive",
                                "/books/add/comment/{id}",
                                "/favorites/all/{readerId}",
                                "/favorites/add/favorite",
                                "/favorites/delete").hasAnyRole("User", "Admin")
                        .requestMatchers(
                                "books/add",
                                "books/edit/{id}",
                                "books/delete/{id}",
                                "categories/add",
                                "categories/edit/{id}",
                                "categories/delete/{id}").hasRole("Admin")
                        .anyRequest().permitAll()
                ).userDetailsService(readerService)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return https.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(readerService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
