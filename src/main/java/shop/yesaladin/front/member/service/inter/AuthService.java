package shop.yesaladin.front.member.service.inter;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.servlet.http.HttpSession;
import shop.yesaladin.front.member.dto.LoginRequest;
import shop.yesaladin.front.member.util.LoginRequestStatus;

public interface AuthService {
    LoginRequestStatus login(LoginRequest request, HttpSession session) throws JsonProcessingException;
    void logout(HttpSession session);
}
