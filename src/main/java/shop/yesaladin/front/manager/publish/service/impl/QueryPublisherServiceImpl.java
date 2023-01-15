package shop.yesaladin.front.manager.publish.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.manager.publish.dto.PublisherResponseDto;
import shop.yesaladin.front.manager.publish.service.inter.QueryPublisherService;

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

    @Value("${yesaladin.gateway}")
    private String url;

    private final String PATH = "/v1/publishers";

    /**
     * 출판사 전체 조회를 요청하여 응답을 받습니다.
     *
     * @return 응답받은 전체 조회된 Dto list
     * @author 이수정
     * @since 1.0
     */
    @Override
    public List<PublisherResponseDto> findAll() {
        return restTemplate.exchange(
                url + PATH,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<List<PublisherResponseDto>>() {}
        ).getBody();
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
