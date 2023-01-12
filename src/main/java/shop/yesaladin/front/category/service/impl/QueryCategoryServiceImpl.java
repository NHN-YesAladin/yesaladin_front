package shop.yesaladin.front.category.service.impl;

import java.awt.print.Pageable;
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
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class QueryCategoryServiceImpl implements QueryCategoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    private static final ParameterizedTypeReference<List<CategoryResponseDto>> CATEGORIES_LIST_TYPE
            = new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<PaginatedResponseDto<CategoryResponseDto>> PAGING_CATEGORIES_TYPE
            = new ParameterizedTypeReference<>() {};

    @Override
    public List<CategoryResponseDto> getParentCategories() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getUrl() + "/v1/categories")
                .path("/parents")
                .build();

        ResponseEntity<List<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                CATEGORIES_LIST_TYPE
        );
        if (log.isInfoEnabled()) {
            log.info("body : {}", responseEntity.getBody());
        }
        return responseEntity.getBody();
    }

    @Override
    public PaginatedResponseDto<CategoryResponseDto> getChildCategoriesByParentId(
            PageRequestDto pageRequestDto,
            Long parentId
    ) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getUrl() + "/v1/categories")
                .queryParam("parentId", parentId)
//                .queryParam("page", pageRequestDto.getPage() == null ? 1 : pageRequestDto.getPage())
//                .queryParam("size", pageRequestDto.getSize() == null ? 10 : pageRequestDto.getSize())
                .build();

        ResponseEntity<PaginatedResponseDto<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                PAGING_CATEGORIES_TYPE
        );

        if (log.isInfoEnabled()) {
            log.info("body : {}", responseEntity.getBody());
        }
        return responseEntity.getBody();
    }

    private static HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }


}
