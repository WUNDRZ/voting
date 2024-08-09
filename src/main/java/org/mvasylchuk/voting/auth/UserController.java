package org.mvasylchuk.voting.auth;

import lombok.RequiredArgsConstructor;
import org.mvasylchuk.voting.auth.dto.AuthTokenResponseDto;
import org.mvasylchuk.voting.auth.dto.TokenRequestDto;
import org.mvasylchuk.voting.auth.dto.UserDto;
import org.mvasylchuk.voting.auth.dto.UserRegistrationRequestDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public AuthTokenResponseDto register(@RequestBody UserRegistrationRequestDto req) {
        String token = service.register(req.getEmail(), req.getPassword(), req.getUsername());
        return new AuthTokenResponseDto(token);
    }

    @PostMapping("/token")
    public AuthTokenResponseDto login(@RequestBody TokenRequestDto req) {
        String token = service.login(req.getEmail(), req.getPassword());
        return new AuthTokenResponseDto(token);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public UserDto getCurrentUser() {
        return service.currentUser();
    }
}
