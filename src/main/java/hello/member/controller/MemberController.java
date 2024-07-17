package hello.member.controller;

import hello.member.dto.MemberDTO;
import hello.member.dto.loginDTO;
import hello.member.dto.saveDTO;
import hello.member.entity.Member;
import hello.member.repository.MemberRepository;
import hello.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @GetMapping("/member/save")
    public String saveForm(@ModelAttribute("member") saveDTO dto) {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@Validated @ModelAttribute("member") saveDTO dto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes){

        String res = memberService.emailCheck(dto.getMemberEmail());

        if (res == null) {
            bindingResult.addError(new FieldError("member", "memberEmail",
                    "중복 이메일이 존재합니다"));
        }
        log.info("MemberController.save");
        log.info("memberDTO = {}", dto);


        if (bindingResult.hasErrors()) {
            return "save";
        } else {
            memberService.save(dto);
            MemberDTO member = memberService.updateForm(dto.getMemberEmail());
            Long id = member.getId();
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addAttribute("status", true);
            return "redirect:/member/{id}";
        }
    }

    @GetMapping("/member/login")
    public String loginForm(@ModelAttribute("loginDTO") loginDTO form) {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@Validated @ModelAttribute("loginDTO") loginDTO form,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        MemberDTO result = memberService.login(form);
        if (result == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("loginEmail", result.getMemberEmail());
        return "redirect:/";

    }

    @GetMapping("/members")
    public String findAll(Model model) {
        List<MemberDTO> all = memberService.findAll();
        model.addAttribute("memberList", all);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO findMember = memberService.findById(id);
        model.addAttribute("member", findMember);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        model.addAttribute("memberId", memberDTO.getId());
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO,
                         @ModelAttribute Long memberId) {
        memberService.update(memberId, memberDTO);
        return "redirect:/members" + memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String delteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/members";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @ResponseBody
    @PostMapping("/member/email-check")
    public String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        log.info("memberEmail : {}", memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;

    }


}
