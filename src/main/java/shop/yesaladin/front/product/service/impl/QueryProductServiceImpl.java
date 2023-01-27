package shop.yesaladin.front.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.ProductTypeResponseDto;
import shop.yesaladin.front.product.dto.ProductsResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.product.service.inter.QueryProductTypeService;
import shop.yesaladin.front.publish.dto.PublisherResponseDto;
import shop.yesaladin.front.publish.service.inter.QueryPublisherService;
import shop.yesaladin.front.tag.dto.TagResponseDto;
import shop.yesaladin.front.tag.service.inter.QueryTagService;
import shop.yesaladin.front.writing.dto.AuthorResponseDto;
import shop.yesaladin.front.writing.service.inter.QueryAuthorService;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    private final QueryAuthorService queryAuthorService;
    private final QueryPublisherService queryPulisherService;
    private final QueryProductTypeService queryProductTypeService;
    private final QueryTagService queryTagService;

    private final RestTemplate restTemplate;

    @Value("${yesaladin.gateway.shop}")
    private String url;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailResponseDto getProductDetail(long productId) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/" + productId)
                .encode()
                .build()
                .toUri();

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<ProductDetailResponseDto> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                ProductDetailResponseDto.class
        );
        return responseEntity.getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getProductRelatedDtoMap() {
        List<AuthorResponseDto> authors = queryAuthorService.findAll();
        List<PublisherResponseDto> publishers = queryPulisherService.findAll();
        List<ProductTypeResponseDto> types = queryProductTypeService.findAll();
        List<TagResponseDto> tags = queryTagService.findAll();

        return Map.of(
                "authors", authors,
                "publishers", publishers,
                "types", types,
                "tags", tags
        );
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

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<PaginatedResponseDto<ProductsResponseDto>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<PaginatedResponseDto<ProductsResponseDto>>() {
                }
        );

        return products.getBody();
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

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<PaginatedResponseDto<ProductsResponseDto>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<PaginatedResponseDto<ProductsResponseDto>>() {
                }
        );

        return products.getBody();
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
