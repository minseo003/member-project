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
    public MemberDTO(Member member) {
        this.id = member.getId();
        this.memberEmail = member.getMemberEmail();
        this.Password = member.getPassword();
        this.Name = member.getName();
    }
}