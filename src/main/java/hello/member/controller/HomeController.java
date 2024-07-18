package hello.member.controller;

import hello.member.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginMember",required = false) MemberDTO member,
                       Model model){

        if (member == null) {
            return "home";
        } else {
            model.addAttribute("member", member);
            return "main";
        }

    }

}
