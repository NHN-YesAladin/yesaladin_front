package shop.yesaladin.front.common;

import static shop.yesaladin.front.member.jwt.AuthUtil.UUID_CODE;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.yesaladin.front.common.exception.CustomBadRequestException;
import shop.yesaladin.front.common.exception.CustomConflictException;
import shop.yesaladin.front.common.exception.CustomForbiddenException;
import shop.yesaladin.front.common.exception.CustomMethodNotAllowedException;
import shop.yesaladin.front.common.exception.CustomNotFoundException;
import shop.yesaladin.front.common.exception.CustomServerException;
import shop.yesaladin.front.common.exception.CustomUnauthorizedException;
import shop.yesaladin.front.common.exception.InvalidHttpHeaderException;
import shop.yesaladin.front.common.exception.RestException;
import shop.yesaladin.front.common.exception.ValidationFailedException;
import shop.yesaladin.front.common.utils.CookieUtils;

/**
 * 예외 처리를 위한 Controller Advice 입니다.
 *
 * @author 송학현
 * @author 이수정
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

    @ExceptionHandler(CustomNotFoundException.class)
    public String handleNotFoundException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/notfound";
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public String handleBadRequestException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/bad-request";
    }

    @ExceptionHandler(CustomMethodNotAllowedException.class)
    public String handleMethodNotAllowedException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/method-not-allowed";
    }

    @ExceptionHandler(CustomConflictException.class)
    public String handleConflictException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/conflict";
    }

    @ExceptionHandler({BadCredentialsException.class, CustomForbiddenException.class})
    public String handleForbiddenException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/forbidden";
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public String handleAuthException(Exception ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        log.error("", ex);

        Cookie authCookie = cookieUtils.createCookie(UUID_CODE.getValue(), "", 0);
        Cookie cartCookie = cookieUtils.createCookie("CART_NO", "", 0);
        Cookie yaAuthCookie = cookieUtils.createCookie("YA_AUT", "", 0);
        response.addCookie(authCookie);
        response.addCookie(cartCookie);
        response.addCookie(yaAuthCookie);

        HttpSession session = request.getSession();
        session.invalidate();

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
        model.addAttribute("error", ex.getMessage());
        return "redirect:/members/login";
    }

    @ExceptionHandler({CustomServerException.class, InvalidHttpHeaderException.class})
    public String handleServerException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute("error", ex.getMessage());
        return "common/errors/5xx";
    }

    @ExceptionHandler(RestException.class)
    public String handleRestException(Exception e){
        return e.getMessage();
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("[Exception] : ", ex);
        model.addAttribute("error", ex.getMessage());
        return "common/errors/error";
    }
}
