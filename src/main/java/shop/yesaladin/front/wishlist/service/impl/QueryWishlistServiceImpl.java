package shop.yesaladin.front.wishlist.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.wishlist.dto.WishlistResponseDto;
import shop.yesaladin.front.wishlist.service.inter.QueryWishlistService;

/**
 * 위시리스를 가져오는 서비스 구현체
 *
 * @author 김선홍
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class QueryWishlistServiceImpl implements QueryWishlistService {
    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private String url;
    private final String PATH = "/v1/wishlist";

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<WishlistResponseDto> getWishlist(Pageable pageable) {
        String uri = UriComponentsBuilder.fromUriString(url)
                .path(PATH)
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();
        ResponseEntity<ResponseDto<PaginatedResponseDto<WishlistResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<WishlistResponseDto>>>() {}
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }
}
