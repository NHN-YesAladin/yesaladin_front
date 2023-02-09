package shop.yesaladin.front.order.service.impl;

import java.net.URI;
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
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.order.dto.OrderCreateResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;

/**
 * 주문 생성과 관련한 service 구현체 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class CommandOrderServiceImpl {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    private static final ParameterizedTypeReference<ResponseDto<OrderCreateResponseDto>> ORDER_CREATE = new ParameterizedTypeReference<ResponseDto<OrderCreateResponseDto>>() {};

    /**
     * {@inheritDoc}
     */
    public ResponseDto<OrderCreateResponseDto> createMemberOrder(OrderMemberCreateRequestDto request) {
        URI uri = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/orders/member")
                .build()
                .encode()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrderMemberCreateRequestDto> httpEntity = new HttpEntity<>(request, headers);

        return restTemplate.exchange(uri, HttpMethod.POST,httpEntity, ORDER_CREATE).getBody();
    }
}
