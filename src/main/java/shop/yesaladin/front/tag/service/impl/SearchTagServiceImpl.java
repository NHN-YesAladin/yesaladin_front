package shop.yesaladin.front.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.tag.dto.SearchedTagsResponseDto;
import shop.yesaladin.front.tag.service.inter.SearchTagService;

/**
 * 엘라스틱서치 태그 검색 서비스 구현체
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@Service
public class SearchTagServiceImpl implements SearchTagService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchedTagsResponseDto searchTagByName(String name, int offset, int size) {
        UriComponents uriComponents = UriComponentsBuilder.fromOriginHeader(gatewayConfig.getShopUrl())
                .path("/v1/search/tags")
                .queryParam("name", name)
                .queryParam("offset", offset)
                .queryParam("size", size)
                .build();
        ResponseEntity<ResponseDto<SearchedTagsResponseDto>> result = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<SearchedTagsResponseDto>>() {
                }
        );
        return result.getBody().getData();
    }
}
