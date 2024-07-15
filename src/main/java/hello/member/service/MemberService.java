package hello.member.service;

import hello.member.dto.MemberDTO;
import hello.member.entity.Member;
import hello.member.form.loginForm;

import java.util.List;

public interface MemberService {
    void save(MemberDTO memberDTO);
    MemberDTO login(loginForm loginForm);
    List<MemberDTO> findAll();
    MemberDTO findById(Long id);

    MemberDTO updateForm(String Email);

    void update(Long id,MemberDTO updateParam);

    void deleteById(Long id);

    String emailCheck(String memberEmail);

}
