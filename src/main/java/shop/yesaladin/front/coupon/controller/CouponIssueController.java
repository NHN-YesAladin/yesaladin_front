package shop.yesaladin.front.coupon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponIssueResponseDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponIssueController {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;

    @GetMapping(value = "/issuance", params = {"type", "couponid"})
    public CouponIssueResponseDto requestCouponIssue(
            @RequestParam("type") String triggerType, @RequestParam("couponid") Long couponId
    ) {
        log.info("********* request coupon issue to shop server ********");
        // request issue coupon with triggerType and couponid to shop server
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .pathSegment("v1", "member-coupons", "issuance")
                .queryParam("type", triggerType)
                .queryParam("couponid", couponId).build();

        log.info("=== coupon issue request url {} ===", uriComponents);

        restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return null;
    }
}
