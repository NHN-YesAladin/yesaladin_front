package shop.yesaladin.front.category.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.category.dto.SearchedCategoryResponseDto;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchCategoryServiceImplTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void test() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                .path("/v1/search/categories")
                .queryParam("name", "가정")
                .queryParam("offset", 0)
                .queryParam("size", 5)
                .build();
        System.out.println(uriComponents.toUri());
        ResponseEntity<ResponseDto<SearchedCategoryResponseDto>> result =
                restTemplate.exchange(uriComponents.toUriString(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<ResponseDto<SearchedCategoryResponseDto>>() {
                        });
        System.out.print(result.getBody().getData().getCount());
    }
}

// http://localhost:8080/v1/search/categories?name=가정&size=1&offset=0
// http://localhost:8080/v1/search/categories?name=가정&offset=0&size=5
// http://localhost:8080/v1/search/categories?name=가정&offset=0&size=5