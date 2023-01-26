package shop.yesaladin.front.common.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String main(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = (String) authentication.getPrincipal();

        model.addAttribute("userId", userId);
        return "main/index";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage/index";
    }
    @GetMapping("/manager")
    public String manager() {
        return "manager/index";
    }

    @GetMapping("/member")
    public String myPage() {
        return "member";
    }
}
