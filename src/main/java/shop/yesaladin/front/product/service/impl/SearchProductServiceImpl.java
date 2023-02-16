package shop.yesaladin.front.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.SearchProductRequestDto;
import shop.yesaladin.front.product.dto.SearchedProductResponseDto;
import shop.yesaladin.front.product.service.inter.SearchProductService;

import java.util.Objects;

/**
 * 상품 검색 서비스 구현체
 *
 * @author 김선홍
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchProductServiceImpl implements SearchProductService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private String host;
    private static final String PATH = "/v1/search/products";

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<SearchedProductResponseDto> searchProductsByProductField(SearchProductRequestDto requestDto, Pageable pageable) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH)
                .queryParam(requestDto.getSelected(), requestDto.getInput())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();
        ResponseEntity<ResponseDto<PaginatedResponseDto<SearchedProductResponseDto>>> result = restTemplate.exchange(
                url.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<SearchedProductResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(result.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<SearchedProductResponseDto> searchProductByCategoryId(
            Long categoryId,
            Pageable pageable
    ) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH)
                .queryParam("categoryid", categoryId)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();
        ResponseEntity<ResponseDto<PaginatedResponseDto<SearchedProductResponseDto>>> result = restTemplate.exchange(
                url.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<SearchedProductResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(result.getBody()).getData();
    }
}
