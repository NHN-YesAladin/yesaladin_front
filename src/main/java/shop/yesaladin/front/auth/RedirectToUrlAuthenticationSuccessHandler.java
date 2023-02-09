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
