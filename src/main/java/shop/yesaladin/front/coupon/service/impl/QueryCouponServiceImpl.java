package shop.yesaladin.front.coupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponSummaryDto;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;

/**
 * 쿠폰 조회 관련 기능을 처리하는 서비스 구현 클래스입니다.
 *
 * @author 서민지
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class QueryCouponServiceImpl implements QueryCouponService {

    private static final ParameterizedTypeReference<PaginatedResponseDto<CouponSummaryDto>> PAGING_COUPON_TYPE = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    @Override
    public PaginatedResponseDto<CouponSummaryDto> getTriggeredCouponList(Pageable pageable) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getCouponUrl() + "/v1/coupons")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();

        ResponseEntity<PaginatedResponseDto<CouponSummaryDto>> responseEntity = restTemplate.exchange(uriComponents.toUri(),
                HttpMethod.GET,
                null,
                PAGING_COUPON_TYPE
        );

        return responseEntity.getBody();
    }
}
