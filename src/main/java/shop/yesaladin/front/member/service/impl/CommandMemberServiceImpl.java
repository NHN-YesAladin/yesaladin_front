package shop.yesaladin.front.member.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.SignUpRequest;
import shop.yesaladin.front.member.dto.SignUpResponse;
import shop.yesaladin.front.member.service.inter.CommandMemberService;

/**
 * 회원 등록을 위한 service 입니다.
 * 추 후 추가되는 기능으로, RestTemplate을 통해 gateway 서버로 요청을 보냅니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommandMemberServiceImpl implements CommandMemberService {

    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * 회원 등록 데이터를 gateway 서버로 넘겨주기 위한 기능입니다.
     * PasswordEncoder를 통해 비밀번호를 encoding 후 RestTemplate으로 Gateway 서버에 요청을 보냅니다.
     *
     * @param request controller에서 요청 받은 회원가입 사용자 입력 데이터 입니다.
     * @return 회원 등록 API 호출 결과 입니다.
     * @author : 송학현
     * @since : 1.0
     */
    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        log.info("request={}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignUpRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<SignUpResponse> response = restTemplate.exchange(
                gatewayConfig.getUrl() + "/v1/members/",
                HttpMethod.POST,
                entity,
                SignUpResponse.class // Response DTO
        );

        log.info("response={}", response);
        // return responseDto 반환
        return response.getBody();
    }
}
