package com.maskman97a.cg_quiz.config;

import com.maskman97a.cg_quiz.common.Const;
import com.maskman97a.cg_quiz.dto.enums.UserTypeEnum;
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
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


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
                                .requestMatchers("/admin/**").hasAuthority(UserTypeEnum.ADMIN.name())
                                .requestMatchers("/teacher/**").hasAuthority(UserTypeEnum.TEACHER.name())
                                .requestMatchers("/student/**").hasAuthority(UserTypeEnum.STUDENT.name())
                                .anyRequest().authenticated() // Tất cả các yêu cầu khác đều cần xác thực
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")  // Đường dẫn đến trang đăng nhập
                        .loginProcessingUrl("/auth/login")  // URL xử lý đăng nhập
                        .defaultSuccessUrl("/home", true)  // Điều hướng về trang chủ khi thành công
                        .failureUrl("/auth/login?error=true")  // Điều hướng về trang lỗi khi thất bại
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")  // URL để thực hiện đăng xuất
                        .logoutSuccessUrl("/auth/login")  // Điều hướng sau khi đăng xuất thành công
                        .invalidateHttpSession(true)  // Hủy session
                        .deleteCookies("JSESSIONID")  // Xóa cookie phiên
                        .permitAll()
                )
                .authenticationProvider(customAuthenticationProvider);

        return http.build();
    }
}
