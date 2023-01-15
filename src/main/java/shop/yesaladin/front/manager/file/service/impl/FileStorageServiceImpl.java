package shop.yesaladin.front.file.service.impl;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.file.service.inter.FileStorageService;

/**
 * 파일 업로드/다운로드를 요청하는 Service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final RestTemplate restTemplate;
    @Value("${yesaladin.gateway}")
    private String url;
    @Value("${storage-token.storage-url}")
    private String storageUrl;
    @Value("${storage-token.storage-account}")
    private String storageAccount;
    @Value("${storage-token.container-name}")
    private String containerName;

    /**
     * shop에 파일 업로드를 요청하여 응답받은 Dto를 반환합니다.
     *
     * @param domainName 파일을 저장할 컨테이너 내의 도메인 경로
     * @param typeName   파일을 저장할 컨테이너 내의 도메인 경로
     * @param file       요청할 파일
     * @return 응답받은 파일의 url과 업로드 시간을 담은 Dto
     * @author 이수정
     * @since 1.0
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

        ResponseEntity<FileUploadResponseDto> response = restTemplate.exchange(
                url + "/v1/files/file-upload/" + domainName + "/" + typeName,
                HttpMethod.POST,
                httpEntity,
                FileUploadResponseDto.class
        );

        return response.getBody();
    }
}
