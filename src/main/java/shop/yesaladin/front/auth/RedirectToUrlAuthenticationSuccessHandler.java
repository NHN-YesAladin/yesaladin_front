package shop.yesaladin.front.auth;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 인증 성공 시 작동하는 SuccessHandler 입니다. 로그인 이전 페이지로 redirect 합니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Setter
public class RedirectToUrlAuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    private String redirectUrlParameter = "redirect-url";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute(redirectUrlParameter);
        if (Objects.nonNull(redirectUrl) && !redirectUrl.isBlank()) {
            response.sendRedirect(redirectUrl);
            return;
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
