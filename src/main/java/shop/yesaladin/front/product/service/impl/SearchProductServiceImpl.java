package shop.yesaladin.front.product.service.impl;

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
import shop.yesaladin.front.product.dto.SearchProductRequestDto;
import shop.yesaladin.front.product.dto.SearchedProductResponseDto;
import shop.yesaladin.front.product.service.inter.SearchProductService;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchProductServiceImpl implements SearchProductService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private String host;
    private static final String PATH = "v1/search/products";
    private static final String OFFSET = "offset";
    private static final String SIZE = "size";

    @Override
    public SearchedProductResponseDto searchProductsByProductField(SearchProductRequestDto requestDto) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH)
                .queryParam(requestDto.getSelected(), requestDto.getInput())
                .queryParam(OFFSET, requestDto.getOffset() - 1)
                .queryParam(SIZE, requestDto.getSize())
                .build();
        ResponseEntity<ResponseDto<SearchedProductResponseDto>> result = restTemplate.exchange(
                url.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<SearchedProductResponseDto>>() {}
        );
        return result.getBody().getData();
    }
}
