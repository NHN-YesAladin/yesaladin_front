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
 * 회원 등록을 위한 service 구현체 입니다.
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
     * {@inheritDoc}
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
                SignUpResponse.class
        );

        log.info("response={}", response.getBody());
        return response.getBody();
    }

}
