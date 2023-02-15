package shop.yesaladin.front.coupon.service.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponGiveRequestDto;
import shop.yesaladin.front.coupon.dto.CouponUseRequestDto;
import shop.yesaladin.front.coupon.dto.RequestIdOnlyDto;
import shop.yesaladin.front.coupon.service.inter.CommandMemberCouponService;

/**
 * 멤버가 보유한 쿠폰 관련 커맨드를 수행하는 서비스 구현 클래스입니다.
 *
 * @author 김홍대, 서민지
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class CommandMemberCouponServiceImpl implements CommandMemberCouponService {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestIdOnlyDto sendGiveRequest(CouponGiveRequestDto dto) {
        URI requestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .pathSegment("v1", "member-coupons")
                .build().toUri();
        RequestEntity<CouponGiveRequestDto> requestEntity = new RequestEntity<>(
                dto,
                null,
                HttpMethod.POST,
                requestUrl
        );
        ResponseDto<RequestIdOnlyDto> response = restTemplate.exchange(
                requestEntity,
                new ParameterizedTypeReference<ResponseDto<RequestIdOnlyDto>>() {
                }
        ).getBody();

        return response.getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestIdOnlyDto sendUseRequest(CouponUseRequestDto dto) {
        URI requestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .pathSegment("v1", "member-coupons", "usage")
                .build().toUri();
        RequestEntity<CouponUseRequestDto> requestEntity = new RequestEntity<>(
                dto,
                null,
                HttpMethod.POST,
                requestUrl
        );
        ResponseDto<RequestIdOnlyDto> response = restTemplate.exchange(
                requestEntity,
                new ParameterizedTypeReference<ResponseDto<RequestIdOnlyDto>>() {
                }
        ).getBody();

        return response.getData();
    }
}
