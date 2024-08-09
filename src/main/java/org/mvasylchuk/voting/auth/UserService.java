package org.mvasylchuk.voting.auth;

import lombok.RequiredArgsConstructor;
import org.mvasylchuk.voting.auth.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public String register(String email, String password, String username) {
        UserEntity user = new UserEntity(null, username, email, passwordEncoder.encode(password));
        repository.save(user);
        return tokenService.generateToken(user);
    }

    public String login(String email, String password) {
        UserEntity user = repository.getByEmail(email);
        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return tokenService.generateToken(user);
        } else {
            return null;
        }
    }

    public UserDto currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        } else {
            UserEntity user = repository.getByEmail((String) auth.getPrincipal());
            return new UserDto(user);
        }
    }
}
