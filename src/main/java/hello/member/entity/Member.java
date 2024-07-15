package hello.member.entity;

import hello.member.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String memberEmail;
    @Column
    @NotBlank
    private String password;
    @Column
    @NotEmpty
    private String name;

    @Builder
    public Member(String memberEmail, String password, String name) {
        this.memberEmail = memberEmail;
        this.password = password;
        this.name = name;
    }

    public static Member toMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.id = memberDTO.getId(); //?
        member.memberEmail = memberDTO.getMemberEmail();
        member.password = memberDTO.getPassword();
        member.name = memberDTO.getName();
        return member;
    }

    public static void updateMember(Member member, MemberDTO memberDTO) {
        member.memberEmail = memberDTO.getMemberEmail();
        member.password = memberDTO.getPassword();
        member.name = memberDTO.getName();
    }

}
