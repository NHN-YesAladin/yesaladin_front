package shop.yesaladin.front.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.file.service.inter.FileStorageService;
import shop.yesaladin.front.product.dto.*;
import shop.yesaladin.front.product.service.inter.CommandProductService;

import java.io.IOException;
import java.util.Objects;

/**
 * 상품 등록/수정/삭제를 요청하기 위한 Service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommandProductServiceImpl implements CommandProductService {

    private final FileStorageService fileStorageService;

    private final RestTemplate restTemplate;

    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public long register(ProductCreateRequestDto createRequestDto) throws IOException {

        log.info("createRequestDto = {}", createRequestDto.toString());

        MultipartFile thumbnailFile = createRequestDto.getThumbnailFile();
        FileUploadResponseDto thumbnailFileResponse = fileStorageService.fileUpload(
                "product",
                "thumbnail",
                thumbnailFile
        );

        MultipartFile ebookFile = createRequestDto.getEbookFile();
        FileUploadResponseDto ebookFileResponse = new FileUploadResponseDto(null, null);
        if (Objects.nonNull(ebookFile) && !ebookFile.getOriginalFilename().isBlank()) {
            ebookFileResponse = fileStorageService.fileUpload(
                    "product",
                    "ebook",
                    ebookFile
            );
        }

        ProductRequestDto productRequestDto = createRequestDto.getProductCreateRequestDto(
                thumbnailFileResponse,
                ebookFileResponse
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(productRequestDto, httpHeaders);
        ResponseEntity<ResponseDto<ProductOnlyIdDto>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/v1/products",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<ProductOnlyIdDto>>() {
                }
        );
        return Objects.requireNonNull(response.getBody()).getData().getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modify(ProductModifyRequestDto modifyRequestDto, long productId) throws IOException {
        MultipartFile thumbnailFile = modifyRequestDto.getThumbnailFile();
        FileUploadResponseDto thumbnailFileResponse = new FileUploadResponseDto(null, null);
        if (Objects.nonNull(thumbnailFile) && !thumbnailFile.getOriginalFilename().isBlank()) {
            thumbnailFileResponse = fileStorageService.fileUpload(
                    "product",
                    "thumbnail",
                    thumbnailFile
            );
        }

        MultipartFile ebookFile = modifyRequestDto.getEbookFile();
        FileUploadResponseDto ebookFileResponse = new FileUploadResponseDto(null, null);
        if (Objects.nonNull(ebookFile) && !ebookFile.getOriginalFilename().isBlank()) {
            ebookFileResponse = fileStorageService.fileUpload(
                    "product",
                    "ebook",
                    ebookFile
            );
        }

        ProductUpdateDto productRequestDto = modifyRequestDto.getProductUpdateDto(
                thumbnailFileResponse,
                ebookFileResponse
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(productRequestDto, httpHeaders);
        ResponseEntity<ResponseDto<ProductOnlyIdDto>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/v1/products/" + productId,
                HttpMethod.PUT,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<ProductOnlyIdDto>>() {
                }
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void softDelete(long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);
        restTemplate.postForEntity(gatewayConfig.getShopUrl() + "/v1/products/" + productId, httpEntity, Void.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeIsSale(long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);
        restTemplate.exchange(gatewayConfig.getShopUrl() + "/v1/products/" + productId + "/is-sale", HttpMethod.POST, httpEntity, Void.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeIsForcedOutOfStock(long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);
        restTemplate.exchange(gatewayConfig.getShopUrl() + "/v1/products/" + productId + "/is-forced-out-of-stock", HttpMethod.POST, httpEntity, Void.class);
    }

}
