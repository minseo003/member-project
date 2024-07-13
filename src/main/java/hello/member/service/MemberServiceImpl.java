package hello.member.service;

import hello.member.dto.MemberDTO;
import hello.member.entity.Member;
import hello.member.exception.MemberNotFoundException;
import hello.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void save(MemberDTO memberDTO) {
        Member savedMember = new Member(memberDTO);
        memberRepository.save(savedMember);
    }

    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        return null;
    }

    @Override
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO findById(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return new MemberDTO(findMember);
    }

    @Override
    public MemberDTO updateForm(String Email) {
        Member byMemberEmail = memberRepository.findByMemberEmail(Email).orElseThrow(MemberNotFoundException::new);
        return new MemberDTO(byMemberEmail);
    }

    @Transactional
    @Override
    public void update(MemberDTO memberDTO) {
        Member foundMember = memberRepository.findById(memberDTO.getId()).orElseThrow(MemberNotFoundException::new);
        Member.updateMember(foundMember, memberDTO);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);

    }

    @Override
    public String emailCheck(String memberEmail) {
        Optional<Member> findMember = memberRepository.findByMemberEmail(memberEmail);

        if (findMember.isPresent()) return String.valueOf(Optional.empty());
        else return "ok";
    }
}
