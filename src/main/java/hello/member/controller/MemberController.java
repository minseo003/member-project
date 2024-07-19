package hello.member.controller;

import hello.member.dto.MemberDTO;
import hello.member.dto.loginDTO;
import hello.member.dto.saveDTO;
import hello.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


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


        /**
         * 세션이 있으면 있는 세션 반환, 없으면 신규세션 생성해 반환
         * 로그인 화면에서 취소 누른 사람들도 여기에 포함된다.
         */
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", result);
        return "redirect:/";

    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model,
                           HttpSession session) {
        MemberDTO findMember = memberService.findById(id);
        model.addAttribute("member", findMember);
        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "detail";
        } else {
            session.setAttribute("loginMember", findMember);
            return "logindetail";
        }


    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        MemberDTO member = (MemberDTO) session.getAttribute("loginMember");
        model.addAttribute("updateMember", member);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@Validated @ModelAttribute("updateMember") MemberDTO dto,
                         BindingResult bindingResult,HttpServletRequest request) {

            if (bindingResult.hasErrors()) {
                return "update";
            } else {
                memberService.update(dto.getId(), dto);
                HttpSession session = request.getSession();
                session.setAttribute("loginMember", dto);
                return "redirect:/member/" + dto.getId();
            }


    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id,HttpServletRequest request) {
        memberService.deleteById(id);
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping ("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
