package org.mvasylchuk.voting.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequestDto {
    private String username;
    private String email;
    private String password;
}
