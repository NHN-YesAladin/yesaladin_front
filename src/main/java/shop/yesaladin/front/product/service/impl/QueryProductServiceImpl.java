package shop.yesaladin.front.product.service.impl;

import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Value("${yesaladin.gateway}")
    private String url;

    /**
     * 상품 상세 조회를 요청하여 응답을 받습니다.
     *
     * @param productId 찾고자 하는 상품의 Id
     * @return 응답받은 상품 상세 정보를 담은 Dto
     * @author 이수정
     * @since 1.0
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
     * 상품 연관관계의 도메인을 각각 조회한 Dto를 담은 Map을 반환합니다.
     *
     * @return 도메인별 Dto를 담은 Map
     * @author 이수정
     * @since 1.0
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
     * 관리자용 상품 전체 조회를 요청하여 응답받습니다.
     *
     * @param pageRequestDto Pagination을 위한 Dto
     * @param typeId         상품 유형 id
     * @return 응답받은 상품 전체 조회 Dto
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
