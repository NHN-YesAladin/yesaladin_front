package shop.yesaladin.front.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final QueryMemberService queryMemberService;

    @GetMapping
    public String main(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = (String) authentication.getPrincipal();

        model.addAttribute("userId", userId);
        return "main/index";
    }

    /**
     * 마이페이지를 반환시켜줍니다.
     *
     * @return 마이페이지
     * @author 최예린
     * @since 1.0
     */
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
