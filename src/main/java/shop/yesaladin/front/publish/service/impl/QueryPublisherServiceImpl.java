package shop.yesaladin.front.publish.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.publish.dto.PublishersResponseDto;
import shop.yesaladin.front.publish.service.inter.QueryPublisherService;

import java.net.URI;
import java.util.Objects;

/**
 * 출판사 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class QueryPublisherServiceImpl implements QueryPublisherService {

    private final RestTemplate restTemplate;
    private final String PATH = "/v1/publishers";

    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<PublishersResponseDto> findAllForManager(PageRequestDto pageRequestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString(gatewayConfig.getShopUrl())
                .path(PATH + "/manager")
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<PublishersResponseDto>>> publishers = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<PublishersResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(publishers.getBody()).getData();
    }

    /**
     * Content-Type이 application/json인 HttpEntity를 반환합니다.
     *
     * @return Content-Type이 application/json인 HttpEntity
     * @author 이수정
     * @since 1.0
     */
    private static HttpEntity getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        return httpEntity;
    }
}
