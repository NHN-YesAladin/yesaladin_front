package shop.yesaladin.front.wishlist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.wishlist.service.inter.WishlistService;

/**
 * 위시리스트 서비스 구현체
 *
 * @author 김선홍
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private String url;
    private final String PATH = "/v1/wishlist";
    private final String PRODUCT_ID = "productid";

    @Override
    public Long save(Long productId) {
        String uri = UriComponentsBuilder.fromUriString(url)
                .path(PATH)
                .queryParam(PRODUCT_ID, productId)
                .toUriString();
        return null;
    }

    @Override
    public void delete(Long productId) {

    }

    @Override
    public Boolean isExist(Long productId) {
        return null;
    }
}
