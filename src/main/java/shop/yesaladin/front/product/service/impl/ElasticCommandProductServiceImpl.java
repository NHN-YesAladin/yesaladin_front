package shop.yesaladin.front.product.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.product.dto.ProductOnlyIdDto;
import shop.yesaladin.front.product.service.inter.ElasticCommandProductService;

/**
 * 엘라스틱서치에 상품을 수정, 삭제하는 서비스 구현체
 *
 * @author 김선홍
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class ElasticCommandProductServiceImpl implements ElasticCommandProductService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway.shop}")
    private String host;
    private static final String PATH = "/v1/search/products";

    @Override
    public void update(Long id) throws JsonProcessingException {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH + "/" + id)
                .build();
        ResponseEntity<ResponseDto<ProductOnlyIdDto>> responseEntity = restTemplate.exchange(
                url.toUri(),
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<ResponseDto<ProductOnlyIdDto>>(){}

        );
    }

    @Override
    public void changeIsSale(Long id) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH + "/is-sale/" + id)
                .build();
        ResponseEntity<ResponseDto<ProductOnlyIdDto>> responseEntity = restTemplate.exchange(
                url.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<ProductOnlyIdDto>>(){}

        );
    }

    @Override
    public void changeIsForcedOutOfStock(Long id) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH + "/is-forced-out-of-stock/" + id)
                .build();
        ResponseEntity<ResponseDto<ProductOnlyIdDto>> responseEntity = restTemplate.exchange(
                url.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<ProductOnlyIdDto>>(){}

        );
    }

    @Override
    public void delete(Long id) {
        UriComponents url = UriComponentsBuilder.fromHttpUrl(host)
                .path(PATH + "/" + id)
                .build();
        ResponseEntity<ResponseDto<Void>> responseEntity = restTemplate.exchange(
                url.toUri(),
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<ResponseDto<Void>>(){}

        );
    }
}
