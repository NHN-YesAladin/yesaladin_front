package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberEmailUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberNameUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberNicknameUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberPhoneUpdateRequestDto;
import shop.yesaladin.front.member.dto.MemberUpdateResponseDto;
import shop.yesaladin.front.member.dto.MemberWithdrawResponseDto;
import shop.yesaladin.front.member.dto.SignUpRequestDto;
import shop.yesaladin.front.member.dto.SignUpResponseDto;
import shop.yesaladin.front.member.service.inter.CommandMemberService;
import shop.yesaladin.front.oauth.dto.Oauth2SignUpRequestDto;

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
    public SignUpResponseDto signUp(SignUpRequestDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignUpRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<SignUpResponseDto>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/v1/members/",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        log.info("response={}", response.getBody().getData());
        return response.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignUpResponseDto signUp(Oauth2SignUpRequestDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Oauth2SignUpRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<SignUpResponseDto>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/v1/members/oauth2",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        log.info("response={}", response.getBody().getData());
        return response.getBody().getData();
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

        ResponseEntity<ResponseDto<MemberWithdrawResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                }
        );

        log.info("response={}", response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<MemberUpdateResponseDto> editNickname(MemberNicknameUpdateRequestDto request) {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/nickname")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberNicknameUpdateRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<MemberUpdateResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return Objects.requireNonNull(response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<MemberUpdateResponseDto> editName(MemberNameUpdateRequestDto request) {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/name")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberNameUpdateRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<MemberUpdateResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return Objects.requireNonNull(response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<MemberUpdateResponseDto> editEmail(MemberEmailUpdateRequestDto request) {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/email")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberEmailUpdateRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<MemberUpdateResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return Objects.requireNonNull(response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<MemberUpdateResponseDto> editPhone(MemberPhoneUpdateRequestDto request) {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/phone")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberPhoneUpdateRequestDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ResponseDto<MemberUpdateResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return Objects.requireNonNull(response.getBody());
    }
}
