package shop.yesaladin.front.member.service.impl;

import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;
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
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.member.dto.MemberGradeHistoryResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberGradeHistoryService;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Service
public class QueryMemberGradeHistoryServiceImpl implements QueryMemberGradeHistoryService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<MemberGradeHistoryResponseDto>>> GRADE_HISTORY_TYPE = new ParameterizedTypeReference<>() {
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<MemberGradeHistoryResponseDto> getMemberGradeHsitoryHistories(
            Pageable pageable,
            LocalDate startDate,
            LocalDate endDate
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PeriodQueryRequestDto> entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(gatewayConfig.getShopUrl())
                .path("/v1/member-grades")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .encode().build()
                .expand("").toUri();

        return Objects.requireNonNull(restTemplate.exchange(
                                uri,
                                HttpMethod.GET,
                                entity,
                                GRADE_HISTORY_TYPE
                        )
                        .getBody())
                .getData();
    }
}
