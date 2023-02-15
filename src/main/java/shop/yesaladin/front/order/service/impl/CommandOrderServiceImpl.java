package shop.yesaladin.front.order.service.impl;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.common.exception.ClientException;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.order.dto.OrderCreateResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;
import shop.yesaladin.front.order.dto.OrderStatusChangeLogResponseDto;
import shop.yesaladin.front.order.dto.OrderStatusCode;
import shop.yesaladin.front.order.dto.OrderUpdateResponseDto;
import shop.yesaladin.front.order.service.inter.CommandOrderService;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<OrderCreateResponseDto> createMemberOrder(OrderMemberCreateRequestDto request) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/orders/member")
                .build()
                .encode()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderMemberCreateRequestDto> httpEntity = new HttpEntity<>(request, headers);

        ResponseDto<OrderCreateResponseDto> response = new ResponseDto<>();
        try {
            response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    httpEntity,
                    ORDER_CREATE
            ).getBody();
        } catch (ClientException ce) {
            return response;
        }
        return response;
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
