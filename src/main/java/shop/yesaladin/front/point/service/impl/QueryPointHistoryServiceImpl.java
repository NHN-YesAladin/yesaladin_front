package shop.yesaladin.front.point.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.point.dto.PointHistoryResponseDto;
import shop.yesaladin.front.point.dto.PointResponseDto;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Service
public class QueryPointHistoryServiceImpl implements QueryPointHistoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    private static final ParameterizedTypeReference<PaginatedResponseDto<PointHistoryResponseDto>> POINT_HISTORY_TYPE = new ParameterizedTypeReference<>() {
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<PointHistoryResponseDto> getPointHistories(
            Pageable pageable,
            String loginId,
            String code
    ) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/points/" + loginId)
                .queryParam("code", code)
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
    public PaginatedResponseDto<PointHistoryResponseDto> getAllPointHistories(Pageable pageable,
            String loginId
    ) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(
                        gatewayConfig.getShopUrl() + "/v1/points/" + loginId)
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
    public ResponseEntity<PointResponseDto> getMemberPoint() {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .path("/v1/points/{loginId}")
                .build()
                .expand("");

        ResponseEntity<PointResponseDto> response = restTemplate.exchange(
                components.toUri(),
                HttpMethod.GET,
                getHttpEntity(),
                PointResponseDto.class
        );

        return response;
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(headers);
    }
}
