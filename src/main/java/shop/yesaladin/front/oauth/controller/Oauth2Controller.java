package shop.yesaladin.front.oauth.controller;

import java.util.Map;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.member.dto.MemberResponseDto;
import shop.yesaladin.front.oauth.Oauth2Factory;
import shop.yesaladin.front.oauth.dto.Oauth2LoginRequestDto;
import shop.yesaladin.front.oauth.service.Oauth2Service;

/**
 * OAuth2 login을 위한 Controller 클래스 입니다. (미완)
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/login/oauth2")
public class Oauth2Controller {

    private final Oauth2Factory oauth2Factory;

    /**
     * OAuth2 Provider의 종류에 따라 redirect url 지정 후 사용자 정보를 가져옵니다.
     * 해당 정보에 따라 login 또는 회원 가입 수행을 위한 페이지로 forwarding 시키기 위한 기능입니다.
     *
     * @param code OAuth2에서 발급 받은 code 입니다.
     * @param request HttpServletRequest
     * @return OAuth2 login을 수행하기 위한 페이지
     * @author 송학현
     * @since 1.0
     */
    @GetMapping("/code")
    public String oauth2Login(
            @RequestParam String code,
            HttpServletRequest request,
            Model model
    ) {
        log.info("code={}", code);
        String provider = getProviderFromCookie(request.getCookies());
        if (Objects.isNull(provider)) {
            return "redirect:/";
        }

        Oauth2Service oauth2Service = oauth2Factory.getOauth2Service(provider);

        String token = oauth2Service.getAccessToken(oauth2Service.tokenProcessingUrl(code));
        log.info("token={}", token);

        Map<String, Object> userInfo = oauth2Service.getUserInfo(
                token,
                oauth2Service.getUserInfoProcessingUrl()
        );

        log.info("userInfo={}", userInfo);
        Oauth2LoginRequestDto oauth2LoginRequestDto = oauth2Service.createOauth2Dto(userInfo);
        log.info("dto={}", oauth2LoginRequestDto);

        boolean isAlreadyMember = oauth2Service.isAlreadyMember(oauth2LoginRequestDto.getEmail());

        if (!isAlreadyMember) {
            // TODO: email이 없는 경우 매번 회원가입 페이지로 넘어가서 새로 등록해야하는 한계가 있음
            model.addAttribute("oauthMember", oauth2LoginRequestDto);
            return "/auth/oauth-signup";
        }

        MemberResponseDto member = oauth2Service.getMember(oauth2LoginRequestDto.getEmail());
        model.addAttribute("oauthMember", member);
        return "/auth/oauth-login";
    }

    /**
     * cookie에 들어있는 OAuth2 provider 이름을 반환하기 위한 기능입니다.
     *
     * @param cookies 브라우저에 존재하는 Cookie의 목록입니다.
     * @return OAuth2 provider의 종류
     * @author : 송학현
     * @since : 1.0
     */
    private String getProviderFromCookie(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (Objects.equals("provider", cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
