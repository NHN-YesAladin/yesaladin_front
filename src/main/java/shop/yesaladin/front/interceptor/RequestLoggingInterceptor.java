package shop.yesaladin.front.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * HttpServletRequest가 동작할 때마다 log를 남기기 위한 custom interceptor 입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {

    /**
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler interceptor가 진입하려는 Controller가 담겨있는 handler
     * @return Controller 진입 여부
     * @throws Exception interceptor 작동 시 발생할 수 있는 예외 입니다.
     * @author 김홍대
     * @since 1.0
     */
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        log.info("x-forwarded-for {}", request.getHeader("x-forwarded-for"));
        log.info(
                "[client-ip]: {} | [request-url]: {}",
                request.getHeader("x-forwarded-for"),
                request.getServletPath()
        );

        return true;
    }
}
