package shop.yesaladin.front.product.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.product.dto.SearchedProductResponseDto;

@SpringBootTest
class SearchProductServiceImplTest {
    @Autowired
    RestTemplate restTemplate;

    @Value("${yesaladin.gateway.shop}")
    private String host;

    @Test
    void test() {

        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path("/search/products")
                .queryParam("title", "깃")
                .queryParam("offset", 0)
                .queryParam("size", 20)
                .build();
        System.out.println(url);

//        ResponseEntity<List<SearchedProductResponseDto>> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<SearchedProductResponseDto>>() {});
//        System.out.println(response.getBody().size());
    }
}