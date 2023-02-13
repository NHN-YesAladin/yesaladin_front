package shop.yesaladin.front.wishlist.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.wishlist.dto.WishlistSaveResponseDto;
import shop.yesaladin.front.wishlist.service.inter.CommandWishlistService;

/**
 * 위시리스트 서비스 구현체
 *
 * @author 김선홍
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommonWishlistServiceImpl implements CommandWishlistService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private String url;
    private final String PATH = "/v1/wishlist";
    private final String PRODUCT_ID = "productid";


    /**
     *{@inheritDoc}
     */
    @Override
    public Long save(Long productId) {
        String uri = UriComponentsBuilder.fromUriString(url)
                .path(PATH)
                .queryParam(PRODUCT_ID, productId)
                .toUriString();
        ResponseEntity<ResponseDto<WishlistSaveResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<WishlistSaveResponseDto>>() {}
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData().getProductId();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void delete(Long productId) {
        String uri = UriComponentsBuilder.fromUriString(url)
                .path(PATH)
                .queryParam(PRODUCT_ID, productId)
                .toUriString();
        ResponseEntity<ResponseDto<Void>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<ResponseDto<Void>>() {}
        );
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Boolean isExist(Long productId) {
        String uri = UriComponentsBuilder.fromUriString(url)
                .path(PATH + "/existence")
                .queryParam(PRODUCT_ID, productId)
                .toUriString();
        ResponseEntity<ResponseDto<Boolean>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<Boolean>>() {}
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }
}
