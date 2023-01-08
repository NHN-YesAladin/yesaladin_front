package shop.yesaladin.front.member.controller;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.member.dto.SignUpRequest;

@Slf4j
@Controller
@RequestMapping("/web/members")
public class MemberWebController {

    @GetMapping("/signup")
    public String memberForm() {
        return "/member/memberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignUpRequest request, BindingResult bindingResult, Model model) {
        log.info("dto={}", request);

        return "/member/signupSuccess";
    }
}
