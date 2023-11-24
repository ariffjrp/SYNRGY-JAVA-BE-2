package binarfud.Challenge_5.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private long id;
    private String username;
    private String email;

    private List<String> roles;

    public JwtResponse(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
