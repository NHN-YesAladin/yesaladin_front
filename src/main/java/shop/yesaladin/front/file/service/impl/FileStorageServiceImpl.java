package shop.yesaladin.front.file.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.file.service.inter.FileStorageService;

import java.io.IOException;
import java.util.Objects;

/**
 * 파일 업로드/다운로드를 요청하는 Service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final RestTemplate restTemplate;
    private final GatewayConfig gatewayConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public FileUploadResponseDto fileUpload(String domainName, String typeName, MultipartFile file)
            throws IOException {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ResponseDto<FileUploadResponseDto>> response = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/v1/files/file-upload/" + domainName + "/" + typeName,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ResponseDto<FileUploadResponseDto>>() {
                }
        );
        return Objects.requireNonNull(response.getBody()).getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] fileDownload(String url) {
        ResponseEntity<ResponseDto<String>> tokenResponse = restTemplate.exchange(
                gatewayConfig.getShopUrl() + "/v1/files/auth-token/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<String>>() {
                }
        );
        String tokenId = Objects.requireNonNull(tokenResponse.getBody()).getData();

        RequestCallback requestCallback = (request) -> request.getHeaders().set("X-Auth-Token", tokenId);
        ResponseExtractor<byte[]> responseExtractor = (response) -> response.getBody().readAllBytes();

        return restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
    }
}
