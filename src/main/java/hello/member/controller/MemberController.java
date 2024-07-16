package hello.member.controller;

import hello.member.dto.MemberDTO;
import hello.member.dto.loginDTO;
import hello.member.dto.saveDTO;
import hello.member.service.MemberService;
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

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member/save")
    public String saveForm(@ModelAttribute("member") saveDTO dto) {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@Validated @ModelAttribute("member") saveDTO dto,
                       Model model, BindingResult bindingResult) {

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
            loginDTO loginDTO = new loginDTO();
            model.addAttribute("loginDTO", loginDTO);
            return "login";
        }
    }

    @GetMapping("/member/login")
    public String loginForm(@ModelAttribute("loginDTO") loginDTO form) {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@Validated @ModelAttribute("loginDTO") loginDTO form,
                        BindingResult bindingResult,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        MemberDTO result = memberService.login(form);
        if (result != null) {
            session.setAttribute("loginEmail", result.getMemberEmail());
            return "main";
        } else {
            return "login";
        }

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
