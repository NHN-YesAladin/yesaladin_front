package shop.yesaladin.front.auth;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import shop.yesaladin.front.member.exception.InvalidLoginRequestException;

/**
 * Form Login을 위해 UsernamePasswordAuthenticationFilter를 대체하여 custom한 filter 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
public class CustomLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public CustomLoginProcessingFilter(String processingUrl) {
        super(processingUrl);
    }

    /**
     * Form Login을 시도 시 작동하는 filter 기능입니다.
     * 입력받은 loginId와 password를 기반으로 인증을 요청합니다.
     *
     * @param request HttpServletRequest 입니다.
     * @param response HttpServletResponse 입니다.
     * @return authenticationManager에게 인가를 위임하여 반환된 결과입니다.
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     *
     * @author : 송학현
     * @since : 1.0
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        if (Objects.isNull(loginId) || Objects.isNull(password)) {
            throw new InvalidLoginRequestException();
        }

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginId, password));
    }
}
