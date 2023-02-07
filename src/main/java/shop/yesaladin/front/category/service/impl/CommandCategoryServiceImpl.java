package shop.yesaladin.front.category.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.dto.CategorySaveRequestDto;
import shop.yesaladin.front.category.service.inter.CommandCategoryService;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.oauth.dto.Oauth2SignUpRequestDto;

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
    private static final String CATEGORY_URL = "/v1/categories";

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto create(CategorySaveRequestDto createRequest) {
        log.info("{}", createRequest);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + CATEGORY_URL)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CategorySaveRequestDto> entity = new HttpEntity<>(createRequest, headers);

        ResponseEntity<ResponseDto<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto modify(Long id, CategorySaveRequestDto modifyRequest) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                gatewayConfig.getShopUrl() + CATEGORY_URL).path("/" + id).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CategorySaveRequestDto> entity = new HttpEntity<>(modifyRequest, headers);

        ResponseEntity<ResponseDto<CategoryResponseDto>> responseEntity = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
                gatewayConfig.getShopUrl() + CATEGORY_URL).path("/" + id).build();
        restTemplate.delete(uriComponents.toUri());
    }


}
