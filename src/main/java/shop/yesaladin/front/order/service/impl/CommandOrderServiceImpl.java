package shop.yesaladin.front.order.service.impl;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.common.exception.ClientException;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.order.dto.*;
import shop.yesaladin.front.order.service.inter.CommandOrderService;

import java.net.URI;

/**
 * 주문 생성과 관련한 service 구현체 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class CommandOrderServiceImpl implements CommandOrderService {

    private static final ParameterizedTypeReference<ResponseDto<OrderCreateResponseDto>> ORDER_CREATE = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<OrderUpdateResponseDto>> ORDER_UPDATE = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<OrderCreateResponseDto> createMemberOrder(
            OrderMemberCreateRequestDto request,
            String type
    ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/orders/member");
        if (Objects.nonNull(type)) {
            // 단건 주문일 경우, 특별하게 처리하기 위함
            builder.queryParam("type", "one");
        }
        URI uri = builder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderMemberCreateRequestDto> httpEntity = new HttpEntity<>(request, headers);

        ResponseDto<OrderCreateResponseDto> responseDto = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                ORDER_CREATE
        ).getBody();
        OrderCreateResponseDto data = responseDto.getData();
        String requestId = getRequestIdForCouponsToRedis(data.getOrderNumber());
        System.out.println("requestId = " + requestId);
        data.setRequestId(requestId);
        return responseDto;
    }

    private String getRequestIdForCouponsToRedis(String orderNumber) {
        return Optional.ofNullable(redisTemplate.opsForHash().get("USE_COUPON_REQ_ID", orderNumber))
                .orElse("")
                .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hideOrder(Long orderId, boolean hidden) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/orders/{orderId}")
                .queryParam("hide", hidden)
                .build()
                .expand(orderId)
                .encode()
                .toUri();
        try {
            restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    null,
                    ORDER_UPDATE
            ).getBody();
        } catch (ClientException ce) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<OrderStatusChangeLogResponseDto> appendOrderStatusCode(Long orderId, OrderStatusCode code) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/orders/{orderId}")
                .queryParam("status", code.toString())
                .build()
                .expand(orderId)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ResponseDto<OrderStatusChangeLogResponseDto>> exchange = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return exchange.getBody();
    }
}
