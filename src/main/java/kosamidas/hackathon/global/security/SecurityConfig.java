package kosamidas.hackathon.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kosamidas.hackathon.global.security.error.CustomAuthenticationEntryPoint;
import kosamidas.hackathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/user").permitAll()
                .antMatchers(POST, "/auth/login").permitAll()
                .antMatchers(GET, "/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers(POST, "/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().hasRole("USER")
                .and()
                .formLogin().disable()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))
                .and()
                .apply(new FilterConfig(jwtProvider, objectMapper));
        return http.build();
    }
}
