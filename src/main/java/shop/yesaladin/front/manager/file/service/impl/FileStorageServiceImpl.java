package shop.yesaladin.front.manager.file.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.manager.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.manager.file.service.inter.FileStorageService;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${storage-token.storage-url}")
    private String storageUrl;
    @Value("${storage-token.storage-account}")
    private String storageAccount;
    @Value("${storage-token.container-name}")
    private String containerName;

    private RestTemplate restTemplate;

    private String getUrl(@NonNull String pathName, @NonNull String fileName) {
        return storageUrl + "/" + storageAccount + "/" + containerName + "/" + pathName + "/" + fileName;
    }

    @Override
    public FileUploadResponseDto fileUpload(String token, String pathName, String filename, final InputStream inputStream) {
        String url = this.getUrl(pathName, filename);

        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
        final RequestCallback requestCallback = new RequestCallback() {
            public void doWithRequest(final ClientHttpRequest request) throws IOException {
                request.getHeaders().add("X-Auth-Token", token);
                IOUtils.copy(inputStream, request.getBody());
            }
        };

        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate = new RestTemplate(requestFactory);

        HttpMessageConverterExtractor<String> responseExtractor
                = new HttpMessageConverterExtractor<String>(String.class, restTemplate.getMessageConverters());

        // API 호출
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);

        return new FileUploadResponseDto(url, LocalDateTime.now().toString());
    }
}
