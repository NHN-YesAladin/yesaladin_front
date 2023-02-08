package shop.yesaladin.front.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.product.dto.RelationsResponseDto;
import shop.yesaladin.front.product.service.inter.QueryRelationService;

import java.util.Objects;


/**
 * 상품 연관관계 조회 요청을 위한 Service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class QueryRelationServiceImpl implements QueryRelationService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<RelationsResponseDto> getRelations(long productId, PageRequestDto pageRequestDto) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/products/" + productId + "/relations/manager")
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .build();

        ResponseEntity<ResponseDto<PaginatedResponseDto<RelationsResponseDto>>> response = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<RelationsResponseDto>>>() {}
        );
        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * Content-Type이 application/json인 HttpEntity를 반환합니다.
     *
     * @return Content-Type이 application/json인 HttpEntity
     * @author 이수정
     * @since 1.0
     */
    private HttpEntity getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(httpHeaders);
    }
}
