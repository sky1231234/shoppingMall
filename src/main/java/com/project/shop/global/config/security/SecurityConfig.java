package com.project.shop.global.config.security;

import com.project.shop.global.config.security.filter.JwtFilter;
import com.project.shop.global.config.security.handler.JwtAccessDeniedHandler;
import com.project.shop.global.config.security.handler.JwtAuthenticationEntryPoint;
import com.project.shop.member.domain.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAtuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/favicon.ico")
                .antMatchers("/error")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/v3/**")
                .antMatchers("/h2-console/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //token 사용 방식이기때문에 csrf를 disable
                .csrf().disable()
                //form 기반 로그인 비활성화/ 커스텀으로 구성한 필터 사용
                .formLogin().disable()
               // 401, 403 Exception 핸들링
                .exceptionHandling()
                .authenticationEntryPoint(jwtAtuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                //세션 사용하지 않고 jwt 사용 - stateless
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // HttpServletRequest를 사용하는 요청들에 대한 접근 제한을 설정함
                .and()
                .authorizeRequests()
                    .mvcMatchers("/api/login").permitAll() // 로그인 api
                    .mvcMatchers("/api/signup").permitAll() // 회원가입 api
                    .mvcMatchers("/api/**").authenticated()
                    .mvcMatchers("/admin/**").hasAuthority("admin")

//                    .anyRequest().authenticated() // 그 외 인증 없이 접근X

                // JwtSecurityConfig 적용
                .and()
                .addFilterBefore(
                        new JwtFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}
