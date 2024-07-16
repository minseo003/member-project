package hello.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class loginDTO {
    @NotEmpty
    private String memberEmail;
    @NotEmpty
    private String password;

    public loginDTO(String memberEmail, String password) {
        this.memberEmail = memberEmail;
        this.password = password;
    }
}
