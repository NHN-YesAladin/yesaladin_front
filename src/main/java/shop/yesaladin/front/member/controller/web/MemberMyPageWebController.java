package shop.yesaladin.front.member.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.member.service.inter.CommandMemberService;

/**
 * 마이페이지 관련 회원 Controller입니다.
 *
 * @author 최예린, 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MemberMyPageWebController {

    private final CommandMemberService commandMemberService;

    /**
     * 회원 탈퇴 페이지를 view로 리턴시켜주기 위한 Get handler 입니다.
     *
     * @return 회원 탈퇴 페이지
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/withdraw")
    public String withdraw() {
        return "mypage/member/member-withdraw";
    }

    /**
     * 회원 탈퇴를 위한 Post handler 입니다.
     *
     * @return 리다이렉트 된 메인 페이지
     * @author : 송학현
     * @since : 1.0
     */
    @PostMapping("/withdraw")
    public String doWithdraw() {
        SecurityContext context = SecurityContextHolder.getContext();
        String loginId = context.getAuthentication().getName();

        log.info("loginId={}", loginId);
        commandMemberService.withdraw(loginId);

        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

        return "redirect:/";
    }
}
