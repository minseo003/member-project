package hello.member.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class loginForm {
    @NotEmpty
    private String memberEmail;
    @NotEmpty
    private String password;

    public loginForm(String memberEmail, String password) {
        this.memberEmail = memberEmail;
        this.password = password;
    }
}
