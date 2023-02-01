package shop.yesaladin.front.writing.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.writing.dto.SearchedAuthorResponseDto;
import shop.yesaladin.front.writing.service.inter.SearchAuthorService;


/**
 * 엘라스틱서치 저자 검색 서비스 구현체
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@Service
public class SearchAuthorServiceImpl implements SearchAuthorService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private final String url;

    /**
     *{@inheritDoc}
     */
    @Override
    public SearchedAuthorResponseDto searchAuthorByName(String name, int offset, int size) {
        UriComponents uriComponents = UriComponentsBuilder.fromOriginHeader(url)
                .path("/v1/search/authors")
                .queryParam("name", name)
                .queryParam("offset", offset)
                .queryParam("size", size)
                .build();
        ResponseEntity<ResponseDto<SearchedAuthorResponseDto>> result = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<SearchedAuthorResponseDto>>() {
                }
        );
        return result.getBody().getData();

    }
}
