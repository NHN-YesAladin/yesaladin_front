package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberGradeHistoryResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberGradeHistoryService;
import shop.yesaladin.front.point.dto.PointHistoryResponseDto;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Service
public class QueryMemberGradeHistoryServiceImpl implements QueryMemberGradeHistoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    private static final ParameterizedTypeReference<PaginatedResponseDto<MemberGradeHistoryResponseDto>> GRADE_HISTORY_TYPE = new ParameterizedTypeReference<>() {};

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberGradeHistoryResponseDto> getMemberGradeHsitoryHistories(
            Pageable pageable,
            String loginId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        PeriodQueryRequestDto request = new PeriodQueryRequestDto(startDate, endDate);
        HttpEntity<PeriodQueryRequestDto> entity = new HttpEntity<>(request,headers);

        URI uri = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/members/{loginId}/grade-histories")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .encode().build()
                .expand(loginId).toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, entity, GRADE_HISTORY_TYPE).getBody();
    }
}
