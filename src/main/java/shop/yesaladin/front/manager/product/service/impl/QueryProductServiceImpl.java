package shop.yesaladin.front.manager.product.service.impl;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.manager.product.service.inter.QueryProductService;
import shop.yesaladin.front.manager.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.manager.product.dto.ProductTypeResponseDto;
import shop.yesaladin.front.manager.tag.dto.TagResponseDto;
import shop.yesaladin.front.manager.product.service.inter.QueryProductTypeService;
import shop.yesaladin.front.manager.tag.service.inter.QueryTagService;
import shop.yesaladin.front.manager.publish.dto.PublisherResponseDto;
import shop.yesaladin.front.manager.publish.service.inter.QueryPublisherService;
import shop.yesaladin.front.manager.writing.dto.AuthorResponseDto;
import shop.yesaladin.front.manager.writing.service.inter.QueryAuthorService;

/**
 * 상품 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class QueryProductServiceImpl implements QueryProductService {

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
        String path = "/v1/products/" + productId;

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<ProductDetailResponseDto> responseEntity = restTemplate.exchange(
                url + path,
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
        HttpEntity httpEntity = getHttpEntity();

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
     * Content-Type이 application/json인 HttpEntity를 반환합니다.
     *
     * @return Content-Type이 application/json인 HttpEntity
     * @author 이수정
     * @since 1.0
     */
    private static HttpEntity getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        return httpEntity;
    }
}
