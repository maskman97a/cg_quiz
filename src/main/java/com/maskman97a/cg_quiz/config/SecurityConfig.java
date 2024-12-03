package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.common.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Tắt CSRF nếu cần.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/home", "/").permitAll()  // Cho phép truy cập các trang này
                        .requestMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico").permitAll()  // Tài nguyên tĩnh
                        .requestMatchers("/question-categories/**").permitAll()  // Cho phép truy cập các URL liên quan đến danh mục
                        .anyRequest().authenticated()  // Các yêu cầu khác cần xác thực
                )
                .formLogin(form -> form
                        .loginPage(Const.LOGIN_ENDPOINT)  // Trang đăng nhập
                        .loginProcessingUrl(Const.LOGIN_ENDPOINT)
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl(Const.LOGIN_ENDPOINT)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
