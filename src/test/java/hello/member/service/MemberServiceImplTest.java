package hello.member.service;

import hello.member.dto.MemberDTO;
import hello.member.dto.loginDTO;
import hello.member.dto.saveDTO;
import hello.member.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class MemberServiceImplTest {
    @Autowired
    MemberService memberService;

    @Test
    void findAll() {
        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        memberService.save(dto1);

        saveDTO dto2 = new saveDTO("test2", "5678", "userB");
        memberService.save(dto2);

        saveDTO dto3 = new saveDTO("test3", "9999", "userC");
        memberService.save(dto3);

        List<MemberDTO> all = memberService.findAll();
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void findById() {

        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        memberService.save(dto1);

        saveDTO dto2 = new saveDTO("test2", "5678", "userB");
        memberService.save(dto2);

        saveDTO dto3 = new saveDTO("test3", "9999", "userC");
        memberService.save(dto3);

        MemberDTO findMember1 = memberService.findById(1L);
        MemberDTO findMember2 = memberService.findById(2L);
        MemberDTO findMember3 = memberService.findById(3L);

        assertThat(findMember1.getName()).isEqualTo("userA");
        assertThat(findMember2.getName()).isEqualTo("userB");
        assertThat(findMember3.getName()).isEqualTo("userC");
        assertThatThrownBy(() -> memberService.findById(4L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void emailCheck() {

        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        memberService.save(dto1);

        saveDTO dto2 = new saveDTO("test2", "5678", "userB");
        memberService.save(dto2);

        saveDTO dto3 = new saveDTO("test3", "9999", "userC");
        memberService.save(dto3);

        String email = memberService.emailCheck("tfdsae1");
        String email2 = memberService.emailCheck("tefds");
        String email3 = memberService.emailCheck("test3");
        assertThat(email).isEqualTo("ok");
        assertThat(email2).isEqualTo("ok");
        assertThat(email3).isEqualTo("Optional.empty");

    }

    @Test
    void login() {


        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        saveDTO dto2 = new saveDTO("test2", "4567", "userB");
        saveDTO dto3 = new saveDTO("test3", "9999", "userC");
        memberService.save(dto1);
        memberService.save(dto2);
        memberService.save(dto3);

        loginDTO test1 = new loginDTO("test1", "1234");
        loginDTO test2 = new loginDTO("test２","4567");
        loginDTO test3 = new loginDTO("test３", "9999");

        MemberDTO login1 = memberService.login(test1);
        MemberDTO login2 = memberService.login(test2);
        MemberDTO login3 = memberService.login(test3);


        assertThat(login1.getName()).isEqualTo("userA");
        assertThat(login2.getName()).isEqualTo("userB");
        assertThat(login3.getName()).isEqualTo("userC");
    }

    @Test
    void updateForm() {
        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        memberService.save(dto1);

        saveDTO dto2 = new saveDTO("test2", "5678", "userB");

        saveDTO dto3 = new saveDTO("test3", "9999", "userC");
        memberService.save(dto3);

        MemberDTO dto = memberService.updateForm("test3");

        assertThat(dto.getName()).isEqualTo("userC");
        assertThatThrownBy(() -> memberService.updateForm("test2"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void update() {
        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        memberService.save(dto1);
        MemberDTO dto2 = new MemberDTO("test1", "1234", "usedfdrA");
        MemberDTO dto3 = new MemberDTO("test2", "1234", "userB");
        MemberDTO dto4 = new MemberDTO("test1", "5678", "userB");
        MemberDTO dto5 = new MemberDTO("tefdst2", "5dfd678", "userfdsB");

        MemberDTO savedMember = memberService.findById(1L);
        Long id = savedMember.getId();
        //memberService.update(id, dto2);
        //MemberDTO found = memberService.findById(1L);
        //assertThat(found.getName()).isEqualTo("usedfdrA");

        //memberService.update(id,dto3);
        //MemberDTO found = memberService.findById(1L);
        //assertThat(found.getMemberEmail()).isEqualTo("test2");

        //memberService.update(id,dto4);
        //MemberDTO found = memberService.findById(1L);
        //assertThat(found.getName()).isEqualTo("userB");
        //assertThat(found.getPassword()).isEqualTo("5678");

        memberService.update(id,dto5);
        MemberDTO found = memberService.findById(1L);
        assertThat(found.getName()).isEqualTo("userfdsB");
        assertThat(found.getMemberEmail()).isEqualTo("tefdst2");
        assertThat(found.getPassword()).isEqualTo("5dfd678");
    }

    @Test
    void delete() {
        saveDTO dto1 = new saveDTO("test1", "1234", "userA");
        memberService.save(dto1);

        saveDTO dto2 = new saveDTO("test2", "5678", "userB");
        memberService.save(dto2);

        saveDTO dto3 = new saveDTO("test3", "9999", "userC");
        memberService.save(dto3);

        memberService.deleteById(2L);
        memberService.deleteById(3L);
        List<MemberDTO> all = memberService.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThatThrownBy(() -> memberService.findById(3L))
                .isInstanceOf(MemberNotFoundException.class);
    }

}