package com.velb.social_network.config;

import com.velb.social_network.service.AuthorizationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Resource
    private AuthorizationService authorizationService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll())
                .httpBasic(withDefaults())
                .csrf(httpSecurityCsrfConfigurer -> {
                    httpSecurityCsrfConfigurer
                            .disable();  //TODO убрать
//                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                            .ignoringRequestMatchers("/api/v1/login",
//                                    "/api/v1/registration");
                })
                .formLogin(login -> {
                    login.loginProcessingUrl("/api/v1/login");
                    login.successHandler((request, response, authentication) ->
                            response.setStatus(HttpServletResponse.SC_OK));
                    login.failureHandler((request, response, exception) ->
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
                });
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authorizationService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
