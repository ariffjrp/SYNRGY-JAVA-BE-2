package binarfud.Challenge_5.model.dto;

import binarfud.Challenge_5.model.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UsersDTO {
    private String username;
    private String emailAddress;
    private String password;
    private Set<ERole> role;

    public UsersDTO(String username, String emailAddress, String password, Set<ERole> role) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }
}
