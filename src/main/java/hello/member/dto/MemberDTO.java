package hello.member.dto;

import hello.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberDTO {
    private Long id;
    @NotEmpty
    private String memberEmail;
    @NotEmpty
    private String password;
    @NotBlank
    private String name;

    public MemberDTO(String memberEmail, String password, String name) {
        this.memberEmail = memberEmail;
        this.password = password;
        this.name = name;
    }

    public static MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setMemberEmail(member.getMemberEmail());
        memberDTO.setPassword(member.getPassword());
        memberDTO.setName(member.getName());
        return memberDTO;
    }
}