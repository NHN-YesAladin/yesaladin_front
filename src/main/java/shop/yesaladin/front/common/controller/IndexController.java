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

    /**
     * 마이페이지를 반환시켜줍니다.
     *
     * @return 마이페이지
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/mypage")
    public String mypage() {
        //회원 등급 조회
        //회원 포인트 조회
        //회원 쿠폰 개수 조회
        return "mypage/index";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager/index";
    }
}
