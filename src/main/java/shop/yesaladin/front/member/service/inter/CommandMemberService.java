package shop.yesaladin.front.member.service.inter;

import shop.yesaladin.front.member.dto.SignUpRequest;

public interface CommandMemberService {

    void signUp(SignUpRequest signUpRequest);
}
