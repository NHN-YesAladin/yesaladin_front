package shop.yesaladin.front.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.ProductModifyInitDto;
import shop.yesaladin.front.product.dto.ProductRecentResponseDto;
import shop.yesaladin.front.product.dto.ProductsResponseDto;
import shop.yesaladin.front.product.dto.RelationsResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;

import java.net.URI;
import java.util.Objects;

/**
 * 상품 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueryProductServiceImpl implements QueryProductService {

    private final String PATH = "/v1/products";

    private final RestTemplate restTemplate;
    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<RelationsResponseDto>>> RELATION_PRODUCTION_CODE = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductRecentResponseDto>>> RECENT_PRODUCTION_CODE = new ParameterizedTypeReference<>() {
    };

    @Value("${yesaladin.gateway.shop}")
    private String url;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailResponseDto getProductDetail(long productId) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8072")
                .path(PATH + "/" + productId)
                .encode()
                .build()
                .toUri();

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<ResponseDto<ProductDetailResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<ProductDetailResponseDto>>() {
                }
        );
        return responseEntity.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findAllForManager(
            PageRequestDto pageRequestDto,
            Integer typeId
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("typeId", typeId)
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findAll(
            PageRequestDto pageRequestDto,
            Integer typeId
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH)
                .queryParam("typeId", typeId)
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );

        log.info("products : " + products.getBody().toString());

        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductModifyInitDto getProductForForm(String productId) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/" + productId + "/manager")
                .encode()
                .build()
                .toUri();

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<ResponseDto<ProductModifyInitDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<ProductModifyInitDto>>() {
                }
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<RelationsResponseDto> findProductByTitle(
            Long id,
            String title,
            Pageable pageable
    ) {
        String uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/{id}/relation")
                .queryParam("title", title)
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .buildAndExpand(id)
                .toUriString();

        ResponseEntity<ResponseDto<PaginatedResponseDto<RelationsResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                RELATION_PRODUCTION_CODE
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductRecentResponseDto> findRecentProduct(Pageable pageable) {
        String uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/recent")
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductRecentResponseDto>>> responseEntity = restTemplate.exchange(
                "http://localhost:8072/v1/products/recent",
                HttpMethod.GET,
                getHttpEntity(),
                RECENT_PRODUCTION_CODE
                );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
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
