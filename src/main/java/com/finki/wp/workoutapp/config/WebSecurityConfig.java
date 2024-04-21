package com.finki.wp.workoutapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private final CustomUsernamePasswordAuthenticationProvider authProvider;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(CustomUsernamePasswordAuthenticationProvider authProvider, PasswordEncoder passwordEncoder) {
        this.authProvider = authProvider;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                //.requestMatchers("/node/*").permitAll()
                                //.requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()

                ).formLogin(
                        form -> form
                                .loginPage("/login").permitAll()
                                .failureUrl("/login?error=BadCredentials")
                                .defaultSuccessUrl("/homePage", true)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/login")
                )
                .exceptionHandling((ex) -> ex
                        .accessDeniedPage("/node/access_denied")
                );

        return http.build();

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }


}
