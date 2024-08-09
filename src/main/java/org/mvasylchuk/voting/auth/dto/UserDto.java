package org.mvasylchuk.voting.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.mvasylchuk.voting.auth.UserEntity;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String username;

    public UserDto(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
