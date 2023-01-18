package shop.yesaladin.front.file.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.yesaladin.front.file.dto.TokenJsonDto;
import shop.yesaladin.front.file.dto.TokenRequest;
import shop.yesaladin.front.file.service.inter.StorageAuthService;

@Service
public class StorageAuthServiceImpl implements StorageAuthService {

    @Value("${storage-token.auth-url}")
    private String authUrl;
    @Value("${storage-token.tenant-id}")
    private String tenantId;
    @Value("${storage-token.username}")
    private String username;
    @Value("${storage-token.password}")
    private String password;

    private RestTemplate restTemplate;

    public TokenRequest makeTokenRequest() {
        TokenRequest tokenRequest = new TokenRequest();

        tokenRequest.getAuth().setTenantId(tenantId);
        tokenRequest.getAuth().getPasswordCredentials().setUsername(username);
        tokenRequest.getAuth().getPasswordCredentials().setPassword(password);

        return tokenRequest;
    }

    @Override
    public String getAuthToken() {
        restTemplate = new RestTemplate();

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(makeTokenRequest(), headers);

        // 토큰 요청
        ResponseEntity<String> response = restTemplate.exchange(authUrl + "/tokens", HttpMethod.POST, httpEntity, String.class);

        TokenJsonDto tokenJsonDto = null;
        try {
            ObjectMapper mapper = new JsonMapper();
            tokenJsonDto = mapper.readValue(response.getBody(), TokenJsonDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return tokenJsonDto.getAccess().getToken().getId();
    }

}

