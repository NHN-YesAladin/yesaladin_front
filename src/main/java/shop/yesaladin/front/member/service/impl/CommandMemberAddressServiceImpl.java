package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
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
@RequiredArgsConstructor
@Service
public class CommandMemberAddressServiceImpl implements CommandMemberAddressService {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createMemberAddress(MemberAddressCreateRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberAddressCreateRequestDto> entity = new HttpEntity<>(request, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/addresses")
                .build()
                .expand("")
                .encode().toUri();

        restTemplate.exchange(
                uri,
                HttpMethod.POST,
                entity,
                MemberAddressResponseDto.class
        ).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateDefaultAddress(Long addressId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/addresses/{addressId}")
                .build()
                .expand("")
                .expand(addressId)
                .encode().toUri();

        restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                getEntity(),
                MemberAddressResponseDto.class
        ).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAddress(Long addressId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/addresses/{addressId}")
                .build()
                .expand("")
                .expand(addressId)
                .encode().toUri();

        restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                getEntity(),
                MemberAddressResponseDto.class
        ).getBody();
    }

    private static HttpEntity<PeriodQueryRequestDto> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }
}
