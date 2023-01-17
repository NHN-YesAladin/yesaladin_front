package shop.yesaladin.front.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(String processingUrl) {
        super(processingUrl);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginId, password));
    }
}
