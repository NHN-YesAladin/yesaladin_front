package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberGradeQueryResponseDto;
import shop.yesaladin.front.member.dto.MemberManagerListResponseDto;
import shop.yesaladin.front.member.dto.MemberManagerResponseDto;
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
    private static final ParameterizedTypeReference<ResponseDto<MemberManagerResponseDto>> MEMBER_MANAGER_RESPONSE_DTO = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<MemberManagerListResponseDto>> MEMBER_MANAGER_LIST_RESPONSE_DTO = new ParameterizedTypeReference<>() {
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberProfileExistResponseDto nicknameCheck(String nickname) {
        log.info("nickname={}", nickname);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkNick/{nickname}")
                .encode()
                .build()
                .expand(nickname)
                .toUri();

        return restTemplate.getForObject(
                uri,
                MemberProfileExistResponseDto.class
        );
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public MemberProfileExistResponseDto loginIdCheck(String loginId) {
        log.info("nickname={}", loginId);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkId/{loginId}")
                .encode()
                .build()
                .expand(loginId)
                .toUri();

        return restTemplate.getForObject(
                uri,
                MemberProfileExistResponseDto.class
        );
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public MemberProfileExistResponseDto emailCheck(String email) {
        log.info("email={}", email);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkEmail/{email}")
                .encode()
                .build()
                .expand(email)
                .toUri();

        return restTemplate.getForObject(
                uri,
                MemberProfileExistResponseDto.class
        );
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public MemberProfileExistResponseDto phoneCheck(String phone) {
        log.info("phone={}", phone);

        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/checkPhone/{phone}")
                .encode()
                .build()
                .expand(phone)
                .toUri();

        return restTemplate.getForObject(
                uri,
                MemberProfileExistResponseDto.class
        );
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

    /**
     *{@inheritDoc}
     */
    @Override
    public MemberManagerResponseDto manageMemberInfoByLoginId(String loginId) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("loginid", loginId)
                .toUriString();
        log.info(uri);
        ResponseEntity<ResponseDto<MemberManagerResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MemberManagerResponseDto manageMemberInfoByPhone(String phone) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("phone", phone)
                .toUriString();
        ResponseEntity<ResponseDto<MemberManagerResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MemberManagerResponseDto manageMemberInfoByNickname(String nickname) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("nickname", nickname)
                .toUriString();
        ResponseEntity<ResponseDto<MemberManagerResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MemberManagerListResponseDto manageMemberInfoByName(String name, int page, int size) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("name", name)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();
        ResponseEntity<ResponseDto<MemberManagerListResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_LIST_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MemberManagerListResponseDto manageMemberInfoBySignUpDate(LocalDate signUpDate ,int page, int size) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("signupdate", signUpDate)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();
        ResponseEntity<ResponseDto<MemberManagerListResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_LIST_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }
}
