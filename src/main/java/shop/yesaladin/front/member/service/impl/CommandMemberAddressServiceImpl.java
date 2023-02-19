package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.common.exception.ClientException;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberAddressCreateRequestDto;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;
import shop.yesaladin.front.member.service.inter.CommandMemberAddressService;

/**
 * 회원 배송지 관련 등록, 수정, 삭제 service 구현체 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommandMemberAddressServiceImpl implements CommandMemberAddressService {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    private static final ParameterizedTypeReference<ResponseDto<MemberAddressResponseDto>> MEMBER_ADDRESS_RESPONSE = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<ResponseDto<Object>> MEMBER_ADDRESS_NULL_RESPONSE = new ParameterizedTypeReference<>() {};

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ResponseDto<MemberAddressResponseDto> createMemberAddress(MemberAddressCreateRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberAddressCreateRequestDto> entity = new HttpEntity<>(request, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/member-addresses")
                .build()
                .encode().toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                MEMBER_ADDRESS_RESPONSE
        ).getBody();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ResponseDto<MemberAddressResponseDto> updateDefaultAddress(Long addressId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/member-addresses/{addressId}")
                .build()
                .expand(addressId)
                .encode().toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                getEntity(),
                MEMBER_ADDRESS_RESPONSE
        ).getBody();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ResponseDto<Object> deleteAddress(Long addressId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/member-addresses/{addressId}")
                .build()
                .expand(addressId)
                .encode().toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                getEntity(),
                MEMBER_ADDRESS_NULL_RESPONSE
        ).getBody();
    }

    private static HttpEntity<PeriodQueryRequestDto> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }
    @ExceptionHandler(ClientException.class)
    public ResponseDto<Object> exceptionHandler(ClientException ce) {
        return ResponseDto.builder()
                .success(false)
                .status(ce.getResponseStatus())
                .errorMessages(List.of(ce.getDisplayErrorMessage()))
                .build();
    }
}
