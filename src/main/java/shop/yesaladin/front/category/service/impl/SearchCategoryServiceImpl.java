package shop.yesaladin.front.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.category.dto.SearchedCategoryResponseDto;
import shop.yesaladin.front.category.service.inter.SearchCategoryService;
import shop.yesaladin.front.config.GatewayConfig;

/**
 * 엘라스틱서치 카테고리 검색 서비스 구현체
 *
 * @author : 김선홍
 * @since :1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchCategoryServiceImpl implements SearchCategoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private static final String PATH = "shop/v1/search/categories";

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchedCategoryResponseDto searchCategoryByName(String name, int offset, int size) {
        UriComponents uriComponents = UriComponentsBuilder.fromOriginHeader(gatewayConfig.getShopUrl())
                .path(PATH)
                .queryParam("name", name)
                .queryParam("offset", offset)
                .queryParam("size", size)
                .build();
        ResponseEntity<ResponseDto<SearchedCategoryResponseDto>> result =
                restTemplate.exchange(uriComponents.toUriString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ResponseDto<SearchedCategoryResponseDto>>() {
                        });
        return result.getBody().getData();
    }
}
