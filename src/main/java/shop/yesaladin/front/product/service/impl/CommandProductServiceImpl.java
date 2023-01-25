package shop.yesaladin.front.product.service.impl;

import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.file.service.inter.FileStorageService;
import shop.yesaladin.front.product.dto.ProductOnlyIdDto;
import shop.yesaladin.front.product.dto.ProductRequestDto;
import shop.yesaladin.front.product.dto.ProductResponseDto;
import shop.yesaladin.front.product.service.inter.CommandProductService;

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

    @Value("${yesaladin.gateway.shop}")
    private String url;

    /**
     * 상품을 등록을 요청하여 등록된 상품정보를 반환합니다.
     *
     * @param productResponseDto 상품 등록 요청 Dto
     * @return 등록 후 응답받은 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    @Override
    public long register(ProductResponseDto productResponseDto) throws IOException {

        MultipartFile thumbnailFile = productResponseDto.getThumbnailFile();
        FileUploadResponseDto thumbnailFileResponse = fileStorageService.fileUpload(
                "product",
                "thumbnail",
                thumbnailFile
        );

        MultipartFile ebookFile = productResponseDto.getEbookFile();
        FileUploadResponseDto ebookFileResponse = new FileUploadResponseDto(null, null);
        if (Objects.nonNull(ebookFile) && !ebookFile.getOriginalFilename().isBlank()) {
            ebookFileResponse = fileStorageService.fileUpload(
                    "product",
                    "ebook",
                    ebookFile
            );
        }

        ProductRequestDto productRequestDto = productResponseDto.getProductCreateRequestDto(
                thumbnailFileResponse,
                ebookFileResponse
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(productRequestDto, httpHeaders);
        ResponseEntity<ProductOnlyIdDto> response = restTemplate.exchange(
                url + "/v1/products",
                HttpMethod.POST,
                httpEntity,
                ProductOnlyIdDto.class
        );

        return response.getBody().getId();
    }

//    /**
//     * 상품 수정을 요청합니다.
//     *
//     * @param productResponseDto 상품 수정 요청 Dto
//     * @param productId 수정할 상품의 Id
//     * @author 이수정
//     * @since 1.0
//     */
//    @Override
//    public void modify(ProductResponseDto productResponseDto, long productId) {}

    /**
     * 상품 삭제를 요청합니다.
     *
     * @param productId 삭제할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    @Override
    public void softDelete(long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);
        restTemplate.postForEntity(url + "/v1/products/" + productId, httpEntity, String.class);
    }

}
