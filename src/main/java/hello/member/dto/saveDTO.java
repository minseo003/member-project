package hello.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class saveDTO {
    @NotBlank
    private String memberEmail;
    @NotBlank
    private String password;
    @NotEmpty
    private String name;

    public saveDTO(String memberEmail, String password, String name) {
        this.memberEmail = memberEmail;
        this.password = password;
        this.name = name;
    }

    public saveDTO(String memberEmail, String password) {
        this.memberEmail = memberEmail;
        this.password = password;
    }
}
