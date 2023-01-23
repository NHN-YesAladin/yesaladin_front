package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.LoginRequest;

public interface AuthService {

    void doLogin(LoginRequest loginRequest, String sessionId);

    void logout();
}
