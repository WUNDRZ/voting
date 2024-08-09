package org.mvasylchuk.voting.platform;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class VotingWebSecurityConfig {
    public static final String JWT_SIGNING_KEY_BEAN_NAME = "JWT_SIGNING_SECRET_KEY";

    private final VotingAuthenticationFilter authenticationFilter;
    private final SecretKey key;

    public VotingWebSecurityConfig(@Value("${voting.jwt.signing-key}") String signingKeyRaw) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKeyRaw));
        this.authenticationFilter = new VotingAuthenticationFilter(this.key);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean(JWT_SIGNING_KEY_BEAN_NAME)
    public SecretKey jwtSigningKey() {
        return this.key;
    }
}
