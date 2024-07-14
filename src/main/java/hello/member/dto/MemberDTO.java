package hello.member.dto;

import hello.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String Password;
    private String Name;

    @Builder
    public MemberDTO(String memberEmail, String password, String name) {
        this.memberEmail = memberEmail;
        this.Password = password;
        this.Name = name;
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