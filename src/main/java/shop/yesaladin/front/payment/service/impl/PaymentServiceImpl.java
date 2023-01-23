package shop.yesaladin.front.payment.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.payment.dto.PaymentCompleteSimpleResponseDto;
import shop.yesaladin.front.payment.dto.PaymentRequestDto;
import shop.yesaladin.front.payment.service.inter.PaymentService;

/**
 * 결제 관련 서비스 메서드를 정의한 서비스 구현체
 *
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public PaymentCompleteSimpleResponseDto confirm(PaymentRequestDto requestDto) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getUrl() + "/v1/payments/confirm")
                .build();

        return restTemplate.postForObject(
                uriComponents.toUri(),
                requestDto,
                PaymentCompleteSimpleResponseDto.class
        );
    }
}
