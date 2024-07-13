package hello.member.service;

import hello.member.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    void save(MemberDTO memberDTO);
    MemberDTO login(MemberDTO memberDTO);
    List<MemberDTO> findAll();
    MemberDTO findById(Long id);

    MemberDTO updateForm(String Email);

    void update(MemberDTO memberDTO);

    void deleteById(Long id);

    String emailCheck(String memberEmail);

}
