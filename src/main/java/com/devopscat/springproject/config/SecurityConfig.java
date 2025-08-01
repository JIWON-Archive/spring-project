package com.devopscat.springproject.config;

import io.swagger.v3.oas.models.PathItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
// SpringSecurity를 사용하려면 써야함
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // 스프링시큐리티 기능을 사용하고자 할 때 이 메소드 안에 작성
        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            // cors는 특정 서버로만 데이터를 넘길 수 있도록 설정할 수 있다.
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(authz-> authz
                .requestMatchers("/", "/loginPage", "/logout","noticeCheckPage", "register", "menu/all").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/css/**", "/js/**", "/WEB-INF/**").permitAll()
                .requestMatchers("/noticerAdd", "noticeModifyPage").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST,"/menu/add").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.POST,"/menu/update").hasAnyAuthority("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE,"/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")
                .anyRequest().authenticated()
            )
            .formLogin(
                login -> login.loginPage("/loginPage") // url을 작성해서 로그인페이지로 이동할 때
            .loginProcessingUrl("/login")
            .failureUrl("/loginPage?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(authenticationSuccessHandler())
            .permitAll()
            )
        // logout URL을 통해서 로그아웃 된다.
        .logout(logout -> logout
        //  .logoutRequestMatcher(new AntPathRequestMatcher("/logout") // 더 이상 권장되지 않음
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")       // 로그아웃 성공 후 이 URL로 리다이렉팅
            .invalidateHttpSession(true) // 세션 무효화
            .deleteCookies("JSESSIONID") // 쿠키삭제
            .permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                // 로그인이 성공했을 때 우리가 특별 기능을 넣고 싶을 때(세션, 권한 기능)
                HttpSession session = request.getSession(); // 세션 기능을 가지고 온 것
                boolean isManager = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority ->
                                grantedAuthority.getAuthority().equals("ADMIN") ||
                                        grantedAuthority.getAuthority().equals("MANAGER"));
                if(isManager) {
                    session.setAttribute("MANAGER", true);
                }
                session.setAttribute("username", authentication.getName());
                session.setAttribute("isAuthenticated", true);
                response.sendRedirect(request.getContextPath() + "/");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // http://localhost:8080 서버에서는 데이터를 주고 받을 수 있게 만든 것
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "https://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
