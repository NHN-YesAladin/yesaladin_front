package shop.yesaladin.front.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.product.dto.ProductTypeResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductTypeService;

import java.util.List;
import java.util.Objects;

/**
 * 상품 유형 조회 요청을 위한 Service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class QueryProductTypeServiceImpl implements QueryProductTypeService {

    private final RestTemplate restTemplate;
    private final String PATH = "/v1/product-types";

    private final GatewayConfig gatewayConfig;

    /**
     * 상품 유형 전체 조회를 요청하여 응답을 받습니다.
     *
     * @return 응답받은 전체 조회된 Dto list
     * @author 이수정
     * @since 1.0
     */
    @Override
    public List<ProductTypeResponseDto> findAll() {
        ResponseEntity<ResponseDto<List<ProductTypeResponseDto>>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + PATH,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<List<ProductTypeResponseDto>>>() {
                }
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
