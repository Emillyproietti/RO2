package br.senai.sp.gestaoro.config;

import br.senai.sp.gestaoro.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                //.requestMatchers(new AntPathRequestMatcher("/**"))
                //.requestMatchers(new AntPathRequestMatcher("/usuario/**"))
                .requestMatchers(new AntPathRequestMatcher("/assets/**"));

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/aqv/**")).hasAnyAuthority("AQV")
                        .requestMatchers(new AntPathRequestMatcher("/aluno/**")).hasAnyAuthority("AQV")
                        .requestMatchers(new AntPathRequestMatcher("/professor/**")).hasAnyAuthority("AQV")
                        .requestMatchers(new AntPathRequestMatcher("/turma/**")).hasAnyAuthority("AQV")
                        .requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyAuthority("AQV")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userService)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/ro")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                );

        return httpSecurity.build();
    }



}