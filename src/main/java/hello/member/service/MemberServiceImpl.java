package hello.member.service;

import hello.member.dto.MemberDTO;
import hello.member.dto.loginDTO;
import hello.member.dto.saveDTO;
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
    public void save(saveDTO dto) {
            Member savedMember = Member.builder()
                    .memberEmail(dto.getMemberEmail())
                    .password(dto.getPassword())
                    .name(dto.getName())
                    .build();

            memberRepository.save(savedMember);
    }

    @Override
    public MemberDTO login(loginDTO dto) {
        Optional<Member> findMember = memberRepository.findByMemberEmail(dto.getMemberEmail());

        //이메일 검증
        if (findMember.isPresent()) {
            Member member = findMember.get();
            //비밀번호 검증
            if (member.getPassword().equals(dto.getPassword())) {
                MemberDTO dtoMember = MemberDTO.toMemberDTO(member);
                return dtoMember;
            } else {
                return null;
            }
            //이메일이 맞지 않을때
        } else {
            return null;
        }
    }

    @Override
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberDTO::toMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO findById(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return MemberDTO.toMemberDTO(findMember);
    }

    @Override
    public MemberDTO updateForm(String Email) {
        Member byMemberEmail = memberRepository.findByMemberEmail(Email).orElseThrow(MemberNotFoundException::new);
        return MemberDTO.toMemberDTO(byMemberEmail);
    }

    @Transactional
    @Override
    public void update(Long id,MemberDTO updateParam) {
        Member findMember = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        Member.updateMember(findMember, updateParam);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);

    }

    @Override  //회원가입할때 검증 로직
    public String emailCheck(String memberEmail) {
        Optional<Member> findMember = memberRepository.findByMemberEmail(memberEmail);

        if (findMember.isPresent()) return null;
        else return "ok";
    }
}
