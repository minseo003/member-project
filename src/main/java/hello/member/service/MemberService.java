package hello.member.service;

import hello.member.dto.MemberDTO;
import hello.member.dto.loginDTO;
import hello.member.dto.saveDTO;

import java.util.List;

public interface MemberService {
    void save(saveDTO dto);
    MemberDTO login(loginDTO logindto);
    List<MemberDTO> findAll();
    MemberDTO findById(Long id);

    MemberDTO updateForm(String Email);

    void update(Long id,MemberDTO updateParam);

    void deleteById(Long id);

    String emailCheck(String memberEmail);

}
