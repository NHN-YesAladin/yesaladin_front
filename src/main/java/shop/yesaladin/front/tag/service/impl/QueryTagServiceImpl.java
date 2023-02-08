package shop.yesaladin.front.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.tag.dto.TagResponseDto;
import shop.yesaladin.front.tag.dto.TagsResponseDto;
import shop.yesaladin.front.tag.service.inter.QueryTagService;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * 태그 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class QueryTagServiceImpl implements QueryTagService {

    private final RestTemplate restTemplate;
    private final String PATH = "/v1/tags";
    @Value("${yesaladin.gateway.shop}")
    private String url;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TagResponseDto> findAll() {
        return restTemplate.exchange(
                url + PATH,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<List<TagResponseDto>>() {
                }
        ).getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<TagsResponseDto> findAllForManager(PageRequestDto pageRequestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<TagsResponseDto>>> tags = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<TagsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(tags.getBody()).getData();
    }

    /**
     * Content-Type이 application/json인 HttpEntity를 반환합니다.
     *
     * @return Content-Type이 application/json인 HttpEntity
     * @author 이수정
     * @since 1.0
     */
    private HttpEntity getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        return httpEntity;
    }
}
