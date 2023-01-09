package shop.yesaladin.front.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.member.dto.SignUpRequest;
import shop.yesaladin.front.member.service.inter.CommandMemberService;

/**
 * 회원 관련 페이지를 위한 Controller 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/web/members")
public class MemberWebController {

    private final CommandMemberService commandMemberService;

    /**
     * 회원 등록 폼 페이지를 view로 리턴시켜주기 위한 Get handler 입니다.
     *
     * @return 회원 등록 폼 페이지
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/signup")
    public String memberForm() {
        return "/member/memberForm";
    }

    /**
     * 회원 등록을 위한 Post handler 입니다.
     *
     * @param request 사용자가 입력한 회원정보 등록 폼 데이터의 모음입니다.
     * @param bindingResult @Valid 검증을 위한 파라미터입니다.
     * @param model 등록 처리 이후 회원가입 성공 페이지로 넘어갈 때 필요한 데이터를 view에 넘겨주기 위한 객체 입니다.
     * @return 회원 등록 성공 페이지 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @PostMapping("/signup")
    public String signup(@Valid SignUpRequest request, BindingResult bindingResult, Model model) {
        log.info("dto={}", request);

        commandMemberService.signUp(request);

        return "/member/signupSuccess";
    }
}
