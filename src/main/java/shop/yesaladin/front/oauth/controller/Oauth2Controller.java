package shop.yesaladin.front.oauth.controller;

import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.utils.CookieUtils;
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
    private final CookieUtils cookieUtils;

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
        String provider = cookieUtils.getValueFromCookie(request.getCookies(), "provider");
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
        boolean isAlreadyMember = oauth2Service.isAlreadyMember(oauth2LoginRequestDto);

        if (!isAlreadyMember) {
            model.addAttribute("oauthMember", oauth2LoginRequestDto);
            return "auth/oauth-signup";
        }

        model.addAttribute("oauthMember", oauth2LoginRequestDto);
        return "auth/oauth-login";
    }
}
