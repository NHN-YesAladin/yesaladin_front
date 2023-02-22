package shop.yesaladin.front.member.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberBlockRequestDto;
import shop.yesaladin.front.member.dto.MemberBlockResponseDto;
import shop.yesaladin.front.member.dto.MemberGradeQueryResponseDto;
import shop.yesaladin.front.member.dto.MemberManagerResponseDto;
import shop.yesaladin.front.member.dto.MemberPasswordResponseDto;
import shop.yesaladin.front.member.dto.MemberProfileExistResponseDto;
import shop.yesaladin.front.member.dto.MemberQueryResponseDto;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;
import shop.yesaladin.front.member.dto.MemberUnblockResponseDto;
import shop.yesaladin.front.member.dto.MemberWithdrawResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 회원 정보 조회용 Service 구현체 입니다.
 *
 * @author : 송학현
 * @author : 김선홍
 * @since : 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QueryMemberServiceImpl implements QueryMemberService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private final ObjectMapper objectMapper;
    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> MEMBER_MANAGER_RESPONSE_DTO = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<MemberBlockResponseDto>> MEMBER_MANAGER_BLOCK_RESPONSE_DTO = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<MemberUnblockResponseDto>> MEMBER_MANAGER_UNBLOCK_RESPONSE_DTO = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<MemberWithdrawResponseDto>> MEMBER_MANAGER_WITHDRAW_RESPONSE_DTO = new ParameterizedTypeReference<>() {
    };

    private static final ParameterizedTypeReference<ResponseDto<MemberGradeQueryResponseDto>> MEMBER_GRADE_DTO = new ParameterizedTypeReference<>() {
    };

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
    public ResponseDto<MemberQueryResponseDto> getMemberInfo() {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members")
                .encode()
                .build()
                .expand("")
                .toUri();

        ResponseEntity<ResponseDto<MemberQueryResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return Objects.requireNonNull(response.getBody());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMemberGrade() {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members")
                .queryParam("type", "grade")
                .encode()
                .build()
                .toUri();

        ResponseDto<MemberGradeQueryResponseDto> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getEntity(),
                MEMBER_GRADE_DTO
        ).getBody();
        if (response != null && response.isSuccess()) {
            return response.getData().getGradeEn();
        }
        return "";
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfo(Pageable pageable) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .toUriString();
        ResponseEntity<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    private static HttpEntity getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByLoginId(String loginId, Pageable pageable) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("loginid", loginId)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .toUriString();
        ResponseEntity<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByPhone(String phone, Pageable pageable) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("phone", phone)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", 3)
                .toUriString();
        ResponseEntity<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByNickname(String nickname, Pageable pageable) {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("nickname", nickname)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();
        ResponseEntity<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> responseEntity = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoByName(String name, Pageable pageable) {
        UriComponents uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("name", name)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();
        ResponseEntity<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> responseEntity = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberManagerResponseDto> manageMemberInfoBySignUpDate(LocalDate signUpDate, Pageable pageable) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/manage")
                .queryParam("signupdate", signUpDate)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .toUriString();
        ResponseEntity<ResponseDto<PaginatedResponseDto<MemberManagerResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_MANAGER_RESPONSE_DTO
        );
        return Objects.requireNonNull(Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberBlockResponseDto manageMemberBlockByLoginId(String loginId, MemberBlockRequestDto requestDto)
            throws JsonProcessingException {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/block")
                .encode()
                .build()
                .expand(loginId)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.info(uri);
        String requestJson = objectMapper.writeValueAsString(requestDto);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);
        log.info(httpEntity.toString());
        ResponseEntity<ResponseDto<MemberBlockResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                httpEntity,
                MEMBER_MANAGER_BLOCK_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberUnblockResponseDto manageMemberUnBlockByLoginId(String loginId) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/unblock")
                .encode()
                .build()
                .expand(loginId)
                .toUriString();

        ResponseEntity<ResponseDto<MemberUnblockResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
               null,
                MEMBER_MANAGER_UNBLOCK_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberWithdrawResponseDto manageMemberWithdrawByLoginId(String loginId) {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/withdraw/{loginId}")
                .encode()
                .build()
                .expand(loginId)
                .toUriString();
        ResponseEntity<ResponseDto<MemberWithdrawResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                null,
                MEMBER_MANAGER_WITHDRAW_RESPONSE_DTO
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberStatisticsResponseDto getMemberStatistics() {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/statistics")
                .encode()
                .build()
                .toUriString();

        ResponseEntity<ResponseDto<MemberStatisticsResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberPasswordResponseDto getMemberPassword() {
        String uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/password-check")
                .encode()
                .build()
                .toUriString();

        ResponseEntity<ResponseDto<MemberPasswordResponseDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return Objects.requireNonNull(response.getBody()).getData();
    }
}
