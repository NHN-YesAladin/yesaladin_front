package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberGradeQueryResponseDto;
import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;
import shop.yesaladin.front.member.dto.MemberQueryResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 회원 정보 조회용 Service 구현체 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QueryMemberServiceImpl implements QueryMemberService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberProfileExistResponseDto nicknameCheck(String nickname) {
        log.info("nickname={}", nickname);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkNick/{nickname}")
                .encode()
                .build()
                .expand(nickname)
                .toUri();

        ResponseEntity<ResponseDto<MemberProfileExistResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public MemberProfileExistResponseDto loginIdCheck(String loginId) {
        log.info("nickname={}", loginId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkId/{loginId}")
                .encode()
                .build()
                .expand(loginId)
                .toUri();

        ResponseEntity<ResponseDto<MemberProfileExistResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public MemberProfileExistResponseDto emailCheck(String email) {
        log.info("email={}", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkEmail/{email}")
                .encode()
                .build()
                .expand(email)
                .toUri();

        ResponseEntity<ResponseDto<MemberProfileExistResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public MemberProfileExistResponseDto phoneCheck(String phone) {
        log.info("phone={}", phone);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkPhone/{phone}")
                .encode()
                .build()
                .expand(phone)
                .toUri();

        ResponseEntity<ResponseDto<MemberProfileExistResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberQueryResponseDto getMemberInfo() {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}")
                .encode()
                .build()
                .expand("")
                .toUri();
        return restTemplate.getForObject(
                uri, MemberQueryResponseDto.class
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMemberGrade() {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/grade")
                .encode()
                .build()
                .expand("admin")
                .toUri();
        MemberGradeQueryResponseDto response = restTemplate.getForObject(
                uri, MemberGradeQueryResponseDto.class
        );
        log.info("membergraderesponse : {}", response);
        return response.getGradeEn();
    }
}
