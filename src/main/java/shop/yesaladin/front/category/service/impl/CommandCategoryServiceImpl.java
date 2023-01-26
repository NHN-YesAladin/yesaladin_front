package shop.yesaladin.front.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.category.dto.CategorySaveRequestDto;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.service.inter.CommandCategoryService;
import shop.yesaladin.front.config.GatewayConfig;

/**
 * 카테고리 생성,수정,삭제를 위한 기능을 가지는 서비스
 *
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class CommandCategoryServiceImpl implements CommandCategoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public CategoryResponseDto create(CategorySaveRequestDto createRequest) {
        log.info("{}", createRequest);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/categories")
                .build();

        return restTemplate.postForObject(
                uriComponents.toUri(),
                createRequest,
                CategoryResponseDto.class
        );
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public CategoryResponseDto modify(Long id, CategorySaveRequestDto modifyRequest) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                gatewayConfig.getShopUrl() + "/v1/categories").path("/" + id).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CategorySaveRequestDto> entity = new HttpEntity<>(modifyRequest, headers);

        return restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.PUT,
                entity,
                CategoryResponseDto.class
        ).getBody();
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void delete(Long id) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                gatewayConfig.getShopUrl() + "/v1/categories").path("/" + id).build();
        restTemplate.delete(uriComponents.toUri());
    }


}
