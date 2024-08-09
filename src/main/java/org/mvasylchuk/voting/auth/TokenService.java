package org.mvasylchuk.voting.auth;

import io.jsonwebtoken.Jwts;
import org.mvasylchuk.voting.platform.VotingWebSecurityConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenService {
    private final SecretKey key;

    public TokenService(@Qualifier(VotingWebSecurityConfig.JWT_SIGNING_KEY_BEAN_NAME) SecretKey key) {
        this.key = key;
    }

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setIssuer("voting-app")
                .setSubject(user.getEmail())
                .claim("username", user.getUsername())
                .signWith(this.key)
                .compact();
    }

    public SecretKey getKey() {
        return key;
    }
}
