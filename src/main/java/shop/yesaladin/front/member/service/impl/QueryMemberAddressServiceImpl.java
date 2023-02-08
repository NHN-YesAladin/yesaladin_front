package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberAddressResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberAddressService;

/**
 * 회원 배송지 조회 관련 service 구현체 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class QueryMemberAddressServiceImpl implements QueryMemberAddressService {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    private static final ParameterizedTypeReference<ResponseDto<List<MemberAddressResponseDto>>> MEMBER_ADDRESS_TYPE = new ParameterizedTypeReference<>() {};

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<List<MemberAddressResponseDto>> getMemberAddresses() {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/member-addresses")
                .build()
                .encode()
                .toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                MEMBER_ADDRESS_TYPE
        ).getBody();
    }
}
