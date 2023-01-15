package shop.yesaladin.front.manager.product.service.impl;

import static java.util.UUID.randomUUID;

import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.manager.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.manager.file.service.inter.FileStorageService;
import shop.yesaladin.front.manager.file.service.inter.StorageAuthService;
import shop.yesaladin.front.manager.product.dto.ProductCreateRequestDto;
import shop.yesaladin.front.manager.product.dto.ProductRequestedDto;
import shop.yesaladin.front.manager.product.service.inter.CommandProductService;

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


    private final String THUMBNAIL_FILE_PATH = "product/thumbnail";
    private final String EBOOK_FILE_PATH = "product/ebook";


    private final FileStorageService fileStorageService;
    private final StorageAuthService storageAuthService;

    private final RestTemplate restTemplate;

    @Value("${yesaladin.gateway}")
    private String url;

    /**
     * 상품을 등록을 요청하여 등록된 상품정보를 반환합니다.
     *
     * @param productRequestedDto 상품 등록 요청 Dto
     * @return 등록 후 응답받은
     */
    @Override
    public long register(ProductRequestedDto productRequestedDto) {
        try {
            String token = storageAuthService.getAuthToken();

            MultipartFile thumbnailFile = productRequestedDto.getThumbnailFile();
            FileUploadResponseDto thumbnailFileResponse = fileStorageService.fileUpload(
                    token,
                    THUMBNAIL_FILE_PATH,
                    getFilename(thumbnailFile),
                    thumbnailFile.getInputStream()
            );

            MultipartFile ebookFile = productRequestedDto.getEbookFile();
            FileUploadResponseDto ebookFileResponse = new FileUploadResponseDto(null, null);
            if (Objects.nonNull(ebookFile) && !ebookFile.getOriginalFilename().isBlank()) {
                ebookFileResponse = fileStorageService.fileUpload(
                        token,
                        EBOOK_FILE_PATH,
                        getFilename(ebookFile),
                        ebookFile.getInputStream()
                );
            }

            ProductCreateRequestDto productCreateRequestDto = productRequestedDto.getProductCreateRequestDto(
                    thumbnailFileResponse,
                    ebookFileResponse
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(productCreateRequestDto.toString());
            HttpEntity httpEntity = new HttpEntity(productCreateRequestDto, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(
                    url + "/v1/products",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject result = (JSONObject) jsonParser.parse(response.getBody());

            return (long) result.get("id");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * UUID와 확장자를 합쳐 반환합니다.
     *
     * @param file 변환할 파일 이름
     * @return UUID + 확장자
     */
    private String getFilename(MultipartFile file) {
         return randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }
}
