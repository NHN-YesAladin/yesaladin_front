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
 * 카테고리 조회를 위한 기능을 가지는 서비스 구현체
 *
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

    /**
     * 모든 1차 카테고리를 조회하는 기능
     *
     * @return 카테고리의 일부 정보를 담고있는 dto 리스트
     */
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

    /**
     * 1차 카테고리 id를 통해서 자식 카테고리(=2차 카테고리)를 페이징하여 조회하는 기능
     *
     * @param pageRequestDto page와 size를 담고있는 dto
     * @param parentId 1차 카테고리 id
     * @return 페이징 정보 및 데이터 리스트를 담고있는 dto
     */
    @Override
    public PaginatedResponseDto<CategoryResponseDto> getChildCategoriesByParentId(
            PageRequestDto pageRequestDto,
            Long parentId
    ) {
        log.info("{}", pageRequestDto);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getUrl() + "/v1/categories")
                .queryParam("parentId", parentId)
                .queryParam(
                        "page",
                        pageRequestDto.getPage() == null ? 0 : pageRequestDto.getPage()
                )
                .queryParam(
                        "size",
                        pageRequestDto.getSize() == null ? 10 : pageRequestDto.getSize()
                )
                .build();

        ResponseEntity<PaginatedResponseDto<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                PAGING_CATEGORIES_TYPE
        );

        if (log.isInfoEnabled()) {
            log.info("body : {}", responseEntity.getBody().getDataList());
        }
        return responseEntity.getBody();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }


}
