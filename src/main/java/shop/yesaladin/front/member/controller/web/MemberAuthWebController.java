package shop.yesaladin.front.member.controller.web;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.member.dto.SignUpRequestDto;
import shop.yesaladin.front.member.dto.SignUpResponseDto;
import shop.yesaladin.front.member.service.inter.CommandMemberService;
import shop.yesaladin.front.oauth.dto.Oauth2SignUpRequestDto;

/**
 * 회원 관련 페이지를 위한 Controller 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberAuthWebController {

    private final CommandMemberService commandMemberService;

    /**
     * 회원 등록 폼 페이지를 view로 리턴시켜주기 위한 Get handler 입니다.
     *
     * @return 회원 등록 폼 페이지
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/signup")
    public ModelAndView signupForm(@ModelAttribute(name = "member") SignUpRequestDto member) {
        return new ModelAndView("auth/signup-form");
    }

    /**
     * 회원 등록을 위한 Post handler 입니다.
     *
     * @param request       사용자가 입력한 회원정보 등록 폼 데이터의 모음입니다.
     * @param bindingResult @Valid 검증을 위한 파라미터입니다.
     * @return 회원 등록 성공 페이지 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @PostMapping("/signup")
    public ModelAndView signup(
            @Valid @ModelAttribute(name = "member") SignUpRequestDto request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("auth/signup-form");
        }
        String birth = request.getBirth().replaceAll("-", "");
        request.setBirth(birth);
        log.info("request={}", request);
        SignUpResponseDto response = commandMemberService.signUp(request);

        ModelAndView modelAndView = new ModelAndView("auth/signup-success");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    /**
     * OAuth2 회원 등록을 위한 Post handler 입니다.
     *
     * @param request 사용자가 입력한 OAuth2 회원 정보 등록 폼 데이터의 모음입니다.
     * @param bindingResult @Valid 검증을 위한 파라미터입니다.
     * @param model 등록 처리 이후 회원가입 성공 페이지로 넘어갈 때 필요한 데이터를 view에 넘겨주기 위한 객체 입니다.
     * @return 회원 등록 성공 페이지
     * @author 송학현
     * @since 1.0
     */
    @PostMapping("/oauth2/signup")
    public String oauth2Signup(
            @Valid Oauth2SignUpRequestDto request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "추가 정보 입력 값들이 맞지 않아 가입 실패. 다시 시도하세요.");
            return "common/errors/bad-request";
        }
        String birth = request.getBirth().replaceAll("-", "");
        request.setBirth(birth);
        SignUpResponseDto response = commandMemberService.signUp(request);
        model.addAttribute("response", response);

        return "auth/signup-success";
    }

    /**
     * 로그인 페이지를 view로 리턴시켜주기 위한 Get handler 입니다.
     *
     * @return loginForm view를 반환합니다.
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/login")
    public String loginForm() {
        return "auth/login-form";
    }
}
