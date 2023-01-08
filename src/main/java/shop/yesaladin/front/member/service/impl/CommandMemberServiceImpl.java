package shop.yesaladin.front.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.yesaladin.front.member.dto.SignUpRequest;
import shop.yesaladin.front.member.service.inter.CommandMemberService;

@RequiredArgsConstructor
@Service
public class CommandMemberServiceImpl implements CommandMemberService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequest signUpRequest) {
        //
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        // adapter 전송: adapter는 RestTemplate 사용
        // return responseDto 반환
    }
}
