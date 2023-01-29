package shop.yesaladin.front.member.service.impl;

import java.net.URI;
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
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.category.dto.CategorySaveRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberWithdrawResponseDto;
import shop.yesaladin.front.member.dto.SignUpRequest;
import shop.yesaladin.front.member.dto.SignUpResponse;
import shop.yesaladin.front.member.service.inter.CommandMemberService;

/**
 * 회원 등록, 수정, 삭제를 위한 service 구현체 입니다.
 *
 * @author : 송학현
 * @author 최예린
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
                gatewayConfig.getShopUrl() + "/v1/members/",
                HttpMethod.POST,
                entity,
                SignUpResponse.class
        );

        log.info("response={}", response.getBody());
        return response.getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withdraw(String loginId) {
        log.info("loginId={}", loginId);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/withdraw/{loginId}")
                .encode()
                .build()
                .expand(loginId)
                .toUri();

        ResponseEntity<MemberWithdrawResponseDto> response = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                MemberWithdrawResponseDto.class
        );

        log.info("response={}", response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void edit(MemberUpdateRequestDto request) {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getUrl())
                .path("/v1/members/{loginId}")
                .encode()
                .build()
                .expand("")
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberUpdateRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<MemberWithdrawResponseDto> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                entity,
                MemberWithdrawResponseDto.class
        );
    }
}
