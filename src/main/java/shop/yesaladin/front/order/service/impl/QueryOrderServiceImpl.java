package shop.yesaladin.front.order.service.impl;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.order.dto.OrderSheetResponseDto;
import shop.yesaladin.front.order.dto.OrderSummaryResponseDto;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

/**
 * 주문 조회 서비스 구현체
 *
 * @author 배수한
 * @author 최예린
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class QueryOrderServiceImpl implements QueryOrderService {

    private static final ParameterizedTypeReference<PaginatedResponseDto<OrderSummaryResponseDto>> PAGING_ORDERS_TYPE
            = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<OrderSheetResponseDto>> ORDER_SHEET_TYPE
            = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<OrderSummaryResponseDto> getOrderListInPeriodByMemberId(
            Pageable pageable,
            PeriodQueryRequestDto requestDto
    ) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/member-orders")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize() == 20 ? 10 : pageable.getPageSize())
                .queryParam("startDate", requestDto.getStartDate())
                .queryParam("endDate", requestDto.getEndDate())
                .build();

        log.info("pageable : {}", pageable);
        log.info(
                "startDate : {} | endDate : {} ",
                requestDto.getStartDate(),
                requestDto.getEndDate()
        );

        ResponseEntity<PaginatedResponseDto<OrderSummaryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                PAGING_ORDERS_TYPE
        );
        return responseEntity.getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<OrderSheetResponseDto> getOrderSheetData(
            List<String> isbn,
            List<String> quantity
    ) {
        URI uri = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/order-sheets")
                .queryParam("isbnList", String.join(",", isbn))
                .queryParam("quantityList", String.join(",", quantity))
                .build(true)
                .encode()
                .toUri();

        ResponseEntity<ResponseDto<OrderSheetResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                ORDER_SHEET_TYPE
        );

        return responseEntity.getBody();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }
}
