package shop.yesaladin.front.auth;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import shop.yesaladin.front.member.exception.InvalidLogoutRequestException;

/**
 * 기본적으로 동작하는 LogoutHandler를 custom한 클래스 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * logout 시 동작하는 기능입니다.
     * 세션 정보를 없애고, SecurityContext 역시 clear 합니다.
     *
     * @param request HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param authentication 인증 객체
     * @author : 송학현
     * @since : 1.0
     */
    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) {
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session)) {
            log.info("Already Logged out");
            throw new InvalidLogoutRequestException();
        }

        session.invalidate();

        // TODO: Redis에 uuid로 들어가 있는 정보 무효화

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
    }
}
