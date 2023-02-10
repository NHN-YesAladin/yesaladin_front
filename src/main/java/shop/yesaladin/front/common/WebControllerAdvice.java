package shop.yesaladin.front.common;

import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.yesaladin.front.common.exception.Custom4xxException;
import shop.yesaladin.front.common.exception.CustomForbiddenException;
import shop.yesaladin.front.common.exception.CustomServerException;
import shop.yesaladin.front.common.exception.CustomUnauthorizedException;
import shop.yesaladin.front.common.exception.InvalidHttpHeaderException;
import shop.yesaladin.front.common.exception.ValidationFailedException;
import shop.yesaladin.front.common.utils.CookieUtils;

/**
 * 예외 처리를 위한 Controller Advice 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebControllerAdvice {

    private final CookieUtils cookieUtils;

    @ExceptionHandler(ValidationFailedException.class)
    public String handleValidationFailedException(ValidationFailedException ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/error";
    }

    @ExceptionHandler(Custom4xxException.class)
    public String handle4xxException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/4xx";
    }

    @ExceptionHandler({BadCredentialsException.class, CustomForbiddenException.class})
    public String handleForbiddenException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/forbidden";
    }

    @ExceptionHandler({CustomUnauthorizedException.class})
    public String handleAuthException(Exception ex, Model model, HttpServletResponse response) {
        log.error("", ex);

        Cookie authCookie = cookieUtils.createCookie(UUID_CODE.getValue(), "", 0);
        response.addCookie(authCookie);
        model.addAttribute("error", ex.getMessage());
        return "common/errors/unauthorized";
    }

    @ExceptionHandler({CustomServerException.class, InvalidHttpHeaderException.class})
    public String handleServerException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/5xx";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("[Exception] : ", ex);
        model.addAttribute("error", ex.getMessage());
        return "common/errors/error";
    }
}
