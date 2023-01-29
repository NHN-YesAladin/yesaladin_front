package shop.yesaladin.front.product.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
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
    private static final String PATH = "/v1/search/products";
    private static final String OFFSET = "offset";
    private static final String SIZE = "size";

    @Override
    public SearchedProductResponseDto searchProductsByProductField(SearchProductRequestDto requestDto) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                .path(PATH)
                .queryParam(requestDto.getSelected(), requestDto.getInput())
                .queryParam(OFFSET, requestDto.getOffset()-1)
                .queryParam(SIZE, requestDto.getSize())
                .build();
        return restTemplate.getForObject(
                url.toUriString(),
                SearchedProductResponseDto.class
        );
    }
}
