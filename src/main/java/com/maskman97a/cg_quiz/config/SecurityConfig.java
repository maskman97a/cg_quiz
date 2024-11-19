package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.common.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("**").permitAll()
                                .requestMatchers("/auth/login", "/auth/register", "/auth/forget", "/home", "/").permitAll()
                                .requestMatchers("/js/**", "/css/**", "/images/**", "favicon.ico").permitAll()
                                .anyRequest().authenticated() // Tất cả các yêu cầu khác đều cần xác thực
                )
                .formLogin(form -> form
                        .loginPage(Const.LOGIN_ENDPOINT)  // Định nghĩa trang login
                        .loginProcessingUrl(Const.LOGIN_ENDPOINT)
                        .defaultSuccessUrl("/home", true)  // Sau khi đăng nhập thành công, điều hướng tới trang home
                        .failureUrl("/auth/login?error=true")  // Trang login khi có loi xác thực
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")  // URL để thực hiện đăng xuất
                        .logoutSuccessUrl(Const.LOGIN_ENDPOINT)  // Điều hướng sau khi đăng xuất thành công
                        .invalidateHttpSession(true)  // Vô hiệu hóa session
                        .deleteCookies("JSESSIONID")  // Xóa cookie phiên
                        .permitAll()
                );

        return http.build();
    }
}
