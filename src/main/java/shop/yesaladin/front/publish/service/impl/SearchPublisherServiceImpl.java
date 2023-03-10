package shop.yesaladin.front.publish.service.impl;

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
import shop.yesaladin.front.publish.dto.SearchPublisherResponseDto;
import shop.yesaladin.front.publish.service.inter.SearchPublisherService;

import java.util.Objects;

/**
 * 엘라스틱서치 출판사 검색 서비스 구현체
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@Service
public class SearchPublisherServiceImpl implements SearchPublisherService {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    private static final String PATH = "/v1/search/publishers";

    @Override
    public SearchPublisherResponseDto searchPublisherByName(String name, int offset, int size) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path(PATH)
                .queryParam("name", name)
                .queryParam("offset", offset)
                .queryParam("size", size)
                .build();
        ResponseEntity<ResponseDto<SearchPublisherResponseDto>> result = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<SearchPublisherResponseDto>>() {
                }
        );
        return Objects.requireNonNull(result.getBody()).getData();
    }
}
