package shop.yesaladin.front.point.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.point.dto.PointHistoryResponseDto;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;

/**
 * {@inheritDoc}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class QueryPointHistoryServiceImpl implements QueryPointHistoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<PointHistoryResponseDto>>> POINT_HISTORY_TYPE = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<ResponseDto<Long>> POINT = new ParameterizedTypeReference<>() {};

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<PaginatedResponseDto<PointHistoryResponseDto>> getPointHistories(
            Pageable pageable,
            String code
    ) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl() )
                .path("/v1/points-histories")
                .queryParam("code", code)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build();

        return restTemplate.exchange(
                components.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                POINT_HISTORY_TYPE
        ).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDto<PaginatedResponseDto<PointHistoryResponseDto>> getAllPointHistories(Pageable pageable) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/point-histories")
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .build();

        return restTemplate.exchange(
                components.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                POINT_HISTORY_TYPE
        ).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getMemberPoint() {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/points")
                .build()
                .encode();

        ResponseDto<Long> result =  restTemplate.exchange(
                components.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                POINT
        ).getBody();

        if(result != null && result.isSuccess()) {
            return result.getData();
        }
        return 0;
    }

    private HttpEntity<Void> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }
}
