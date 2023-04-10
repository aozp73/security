package shop.mtcoding.securityapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable(); // postman 접근해야 함!! - CSR 할때!!

        // 2. Form 로그인 설정
        http.formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // POST + X-WWW-Form-urlEncoded
                // .defaultSuccessUrl("/")
                .successHandler((eq, resp, authentication) -> {
                    System.out.println("디버그 : 로그인이 완료되었습니다");
                    resp.sendRedirect("/");
                })
                .failureHandler((req, resp, ex) -> {
                    System.out.println("디버그 : 로그인 실패 -> " + ex.getMessage());
                });

        // 3. 인증, 권한 필터 설정
        http.authorizeRequests(
                authroize -> authroize.antMatchers("/users/**").authenticated()
                        .antMatchers("/manager/**").hasRole("MANAGER")
                        .antMatchers("/admin/**")
                        .access("hasRole('ADMIN') or hasRole('MANAGER')")
                        .anyRequest().permitAll());

        return http.build();
    }
}