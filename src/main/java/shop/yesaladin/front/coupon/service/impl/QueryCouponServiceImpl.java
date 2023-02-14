package shop.yesaladin.front.coupon.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.code.ErrorCode;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.common.exception.ClientException;
import shop.yesaladin.coupon.code.CouponBoundCode;
import shop.yesaladin.coupon.code.TriggerTypeCode;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponBoundResponseDto;
import shop.yesaladin.front.coupon.dto.CouponSummaryDto;
import shop.yesaladin.front.coupon.dto.CouponSummaryWithBoundDto;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;
import shop.yesaladin.front.coupon.dto.MonthlyCouponPolicyDto;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;
import shop.yesaladin.front.product.dto.ProductOnlyTitleDto;

/**
 * 쿠폰 조회 관련 기능을 처리하는 서비스 구현 클래스입니다.
 *
 * @author 서민지
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QueryCouponServiceImpl implements QueryCouponService {

    private static final String MONTHLY_POLICY_KEY = "monthlyCouponPolicy";
    private static final String MONTHLY_COUPON_ID_KEY = "monthlyCouponId";
    private static final String MONTHLY_COUPON_OPEN_DATE_TIME_KEY = "monthlyCouponOpenDateTime";
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<CouponSummaryDto> getTriggeredCouponList(Pageable pageable) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getCouponUrl() + "/v1/coupons?")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();

        ResponseEntity<ResponseDto<PaginatedResponseDto<CouponSummaryDto>>> responseEntity = restTemplate.exchange(uriComponents.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberCouponSummaryDto> getMemberCouponList(
            String loginId, boolean canUse, Pageable pageable
    ) {
        String requestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .pathSegment("v1", "member-coupons")
                .queryParam("usable", canUse)
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();
        ResponseDto<PaginatedResponseDto<MemberCouponSummaryDto>> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<MemberCouponSummaryDto>>>() {
                }
        ).getBody();
        PaginatedResponseDto<MemberCouponSummaryDto> originData = response.getData();
        List<MemberCouponSummaryDto> displayData = originData.getDataList().stream().map(data -> {
            String displayBound = getDisplayBound(data.getCouponBoundCode(), data.getCouponBound());
            return MemberCouponSummaryDto.replaceBoundToDisplayBound(data, displayBound);
        }).collect(Collectors.toList());

        return PaginatedResponseDto.<MemberCouponSummaryDto>builder()
                .dataList(displayData)
                .currentPage(originData.getCurrentPage())
                .totalPage(originData.getTotalPage())
                .totalDataCount(originData.getTotalDataCount())
                .build();
    }

    @Override
    public PaginatedResponseDto<CouponSummaryWithBoundDto> getCouponByTriggerTypeCode(
            TriggerTypeCode triggerTypeCode, Pageable pageable
    ) {
        String requestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getCouponUrl())
                .pathSegment("v1", "coupons")
                .queryParam("triggerType", triggerTypeCode.name())
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();
        ResponseDto<PaginatedResponseDto<CouponSummaryDto>> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<CouponSummaryDto>>>() {
                }
        ).getBody();

        List<CouponSummaryWithBoundDto> couponSummaryWithBoundDtoList = response.getData()
                .getDataList()
                .stream()
                .map(summaryDto -> {
                    String boundRequestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getCouponUrl())
                            .pathSegment("v1",
                                    "coupons",
                                    String.valueOf(summaryDto.getId()),
                                    "bounds"
                            )
                            .toUriString();
                    ResponseDto<CouponBoundResponseDto> boundResponse = restTemplate.exchange(
                            boundRequestUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ResponseDto<CouponBoundResponseDto>>() {
                            }
                    ).getBody();

                    return new CouponSummaryWithBoundDto(summaryDto,
                            boundResponse.getData().getBoundCode(),
                            getDisplayBound(boundResponse.getData().getBoundCode(),
                                    boundResponse.getData().getBound()
                            )
                    );
                })
                .collect(Collectors.toList());

        return PaginatedResponseDto.<CouponSummaryWithBoundDto>builder()
                .totalPage(response.getData().getTotalPage())
                .currentPage(response.getData().getCurrentPage())
                .totalDataCount(response.getData().getTotalDataCount())
                .dataList(couponSummaryWithBoundDtoList)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonthlyCouponPolicyDto getMonthlyCouponPolicy() {
        if (Boolean.TRUE.equals(redisTemplate.opsForHash()
                .hasKey(MONTHLY_POLICY_KEY, MONTHLY_COUPON_ID_KEY))) {
            Map<Object, Object> map = redisTemplate.opsForHash().entries(MONTHLY_POLICY_KEY);

            String couponId = map.get(MONTHLY_COUPON_ID_KEY).toString();
            String openDateTimeStr = map.get(MONTHLY_COUPON_OPEN_DATE_TIME_KEY).toString();

            LocalDateTime openDateTime = LocalDateTime.parse(openDateTimeStr,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            log.info("==== coupon policy, open date time : {} ====", openDateTime);

            return new MonthlyCouponPolicyDto(couponId, openDateTime.toString());
        } else {
            throw new ClientException(ErrorCode.COUPON_NOT_FOUND,
                    "Not found any monthly coupon id on redis."
            );
        }
    }

    private String getDisplayBound(CouponBoundCode boundCode, String bound) {
        if (Objects.isNull(boundCode)) {
            return "포인트 쿠폰";
        }
        if (boundCode.equals(CouponBoundCode.CATEGORY)) {
            return getCategoryNameByCategoryId(bound);
        } else if (boundCode.equals(CouponBoundCode.PRODUCT)) {
            return getProductNameByIsbn(bound);
        }
        return "전체 상품";
    }

    private String getProductNameByIsbn(String isbn) {
        String requestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .pathSegment("v1", "products", "info", isbn)
                .toUriString();
        ResponseDto<ProductOnlyTitleDto> responseBody = restTemplate.exchange(requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<ProductOnlyTitleDto>>() {
                }
        ).getBody();
        return responseBody.getData().getTitle();
    }

    private String getCategoryNameByCategoryId(String categoryId) {
        String requestUrl = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .pathSegment("v1", "categories", categoryId)
                .toUriString();
        ResponseDto<CategoryResponseDto> responseBody = restTemplate.exchange(requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<CategoryResponseDto>>() {
                }
        ).getBody();
        return responseBody.getData().getName();
    }
}
