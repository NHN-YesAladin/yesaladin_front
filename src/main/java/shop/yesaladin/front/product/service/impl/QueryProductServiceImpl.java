package shop.yesaladin.front.product.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.*;
import shop.yesaladin.front.product.service.inter.QueryProductService;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * 상품 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueryProductServiceImpl implements QueryProductService {

    private final String PATH = "/v1/products";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<RelationsResponseDto>>> RELATION_PRODUCTION_CODE = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductRecentResponseDto>>> RECENT_PRODUCTION_CODE = new ParameterizedTypeReference<>() {
    };

    @Value("${yesaladin.gateway.shop}")
    private String url;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDetailResponseDto getProductDetail(long productId) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/" + productId)
                .encode()
                .build()
                .toUri();

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<ResponseDto<ProductDetailResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<ProductDetailResponseDto>>() {
                }
        );
        return responseEntity.getBody().getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findAllForManager(
            PageRequestDto pageRequestDto,
            Integer typeId
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("typeId", typeId)
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findByTitleForManager(
            String title,
            Pageable pageable
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("title", title)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findByISBNForManager(
            String isbn,
            Pageable pageable
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("isbn", isbn)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findByContentForManager(
            String content,
            Pageable pageable
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("content", content)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findByPublisherForManager(
            String publisher,
            Pageable pageable
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("publisher", publisher)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findByAuthorForManager(
            String author,
            Pageable pageable
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/manager")
                .queryParam("author", author)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );
        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<ProductsResponseDto> findAll(
            PageRequestDto pageRequestDto,
            Integer typeId
    ) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH)
                .queryParam("typeId", typeId)
                .queryParam("page", pageRequestDto.getPage())
                .queryParam("size", pageRequestDto.getSize())
                .encode()
                .build()
                .toUri();

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>> products = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<ResponseDto<PaginatedResponseDto<ProductsResponseDto>>>() {
                }
        );

        log.info("products : " + products.getBody().toString());

        return Objects.requireNonNull(products.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductModifyInitDto getProductForForm(String productId) {
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/" + productId + "/manager")
                .encode()
                .build()
                .toUri();

        HttpEntity httpEntity = getHttpEntity();
        ResponseEntity<ResponseDto<ProductModifyInitDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<ProductModifyInitDto>>() {
                }
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponseDto<RelationsResponseDto> findProductByTitle(
            Long id,
            String title,
            Pageable pageable
    ) {
        String uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/{id}/relation")
                .queryParam("title", title)
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .buildAndExpand(id)
                .toUriString();

        ResponseEntity<ResponseDto<PaginatedResponseDto<RelationsResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                RELATION_PRODUCTION_CODE
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductRecentResponseDto> findRecentProduct(Pageable pageable) {
        String uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/recent/product")
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();

        ResponseEntity<ResponseDto<List<ProductRecentResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<>() {
                }
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
    }

    @Override
    public PaginatedResponseDto<ProductRecentResponseDto> findRecentViewProduct(
            RecentViewProductRequestDto dto,
            @PageableDefault Pageable pageable
    ) throws JsonProcessingException {
        String uri = UriComponentsBuilder
                .fromUriString(url)
                .path(PATH + "/recentview/product")
                .queryParam("size", pageable.getPageSize())
                .queryParam("page", pageable.getPageNumber())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = objectMapper.writeValueAsString(dto);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<ResponseDto<PaginatedResponseDto<ProductRecentResponseDto>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                RECENT_PRODUCTION_CODE
        );
        return Objects.requireNonNull(responseEntity.getBody()).getData();
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

        return new HttpEntity(httpHeaders);
    }
}
