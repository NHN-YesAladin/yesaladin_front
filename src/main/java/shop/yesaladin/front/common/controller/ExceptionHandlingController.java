package shop.yesaladin.front.common.controller;

import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 예외 발생 시 예외 페이지로 redirect 할 controller 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Controller
public class ExceptionHandlingController implements ErrorController {

    /**
     * Http Error Status Code에 따라 예외 페이지를 구분하여 반환 하기 위한 기능입니다.
     *
     * @param request HttpServletRequest
     * @return Error Status Code에 따른 예외 페이지를 반환 합니다.
     * @author 송학현
     * @since 1.0
     */
    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object errorStatusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Objects.nonNull(errorStatusCode)) {
            int statusCode = Integer.parseInt(errorStatusCode.toString());

            if (Objects.equals(statusCode, HttpStatus.FORBIDDEN.value())) {
                return new ModelAndView("common/errors/forbidden");
            } else if (Objects.equals(statusCode, HttpStatus.NOT_FOUND.value())) {
                return new ModelAndView("common/errors/4xx");
            } else if (Objects.equals(statusCode, HttpStatus.INTERNAL_SERVER_ERROR.value())) {
                return new ModelAndView("common/errors/5xx");
            }
        }

        return new ModelAndView("common/errors/error");
    }
}
