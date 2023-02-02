package shop.yesaladin.front.category.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
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
import shop.yesaladin.front.category.service.inter.QueryCategoryService;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;

/**
 * 카테고리 조회를 위한 기능을 가지는 서비스 구현체
 *
 * @author 배수한
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QueryCategoryServiceImpl implements QueryCategoryService {

    private static final ParameterizedTypeReference<List<CategoryResponseDto>> CATEGORIES_LIST_TYPE
            = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<PaginatedResponseDto<CategoryResponseDto>> PAGING_CATEGORIES_TYPE
            = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> getParentCategories() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/categories")
                .queryParam("cate", "parents")
                .build();

        ResponseEntity<List<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                CATEGORIES_LIST_TYPE
        );

        return responseEntity.getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<CategoryResponseDto> getChildCategoriesByParentId(
            PageRequestDto pageRequestDto,
            Long parentId
    ) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/categories")
                .queryParam("parentId", parentId)
                .queryParam("page", pageRequestDto.getPage() == null ? 0 : pageRequestDto.getPage())
                .queryParam("size",
                        pageRequestDto.getSize() == null ? 10 : pageRequestDto.getSize()
                )
                .build();

        ResponseEntity<PaginatedResponseDto<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                PAGING_CATEGORIES_TYPE
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
