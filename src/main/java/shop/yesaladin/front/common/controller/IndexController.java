package shop.yesaladin.front.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 메인 페이지, 마이 페이지, 관리자 페이지를 리턴하기 위한 Controller 클래스 입니다.
 *
 * @author : 송학현
 * @author : 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final QueryMemberService queryMemberService;

    /**
     * 메인페이지를 반환시켜줍니다.
     *
     * @return 메인페이지
     * @author 송학현
     * @since 1.0
     */
    @GetMapping
    public String main() {
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

    /**
     * 관리자페이지를 반환시켜줍니다.
     *
     * @return 관리자페이지
     * @author 송학현
     * @since 1.0
     */
    @GetMapping("/manager")
    public String manager() {
        return "manager/index";
    }
}
