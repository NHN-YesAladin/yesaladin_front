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

    private static final ParameterizedTypeReference<List<MemberAddressResponseDto>> MEMBER_ADDRESS_TYPE = new ParameterizedTypeReference<>() {
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberAddressResponseDto> getMemberAddresses() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PeriodQueryRequestDto> entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/addresses")
                .build()
                .expand("")
                .encode().toUri();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                MEMBER_ADDRESS_TYPE
        ).getBody();
    }
}
