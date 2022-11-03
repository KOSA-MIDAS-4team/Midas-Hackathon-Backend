package kosamidas.hackathon.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kosamidas.hackathon.global.security.error.exception.ExceptionFilter;
import kosamidas.hackathon.global.security.jwt.JwtFilter;
import kosamidas.hackathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        JwtFilter jwtTokenFilter = new JwtFilter(jwtProvider);
        ExceptionFilter exceptionFilter = new ExceptionFilter(objectMapper);
        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(exceptionFilter, JwtFilter.class);
    }
}
