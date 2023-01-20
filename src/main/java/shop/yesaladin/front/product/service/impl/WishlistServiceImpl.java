package shop.yesaladin.front.product.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.product.dto.WishlistResponseDto;
import shop.yesaladin.front.product.service.inter.WishlistService;

@RequiredArgsConstructor
@Controller
public class WishlistServiceImpl implements WishlistService {
    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;
    @Override
    public List<WishlistResponseDto> findWishlistByLoginId(String loginId) {
        String url = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/shop/wishlist/{loginid}")
                .buildAndExpand(loginId)
                .toUriString();
        ResponseEntity<WishlistResponseDto []> responseEntity = restTemplate.getForEntity(url, WishlistResponseDto[].class);
        return Arrays.stream(Objects.requireNonNull(responseEntity.getBody())).collect(Collectors.toList());
    }

    @Override
    public void deleteWishlist(Long id) {
        String url = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getUrl())
                .path("/shop/wishlist/{id}")
                .buildAndExpand(id)
                .toUriString();
        restTemplate.delete(url);
    }
}
