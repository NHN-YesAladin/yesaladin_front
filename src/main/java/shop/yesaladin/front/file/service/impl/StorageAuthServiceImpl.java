package shop.yesaladin.front.file.service.impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    public String getAuthToken() throws ParseException {
        restTemplate = new RestTemplate();

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(makeTokenRequest(), headers);

        // 토큰 요청
        ResponseEntity<String> response = restTemplate.exchange(authUrl + "/tokens", HttpMethod.POST, httpEntity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject result = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject access = (JSONObject) result.get("access");
        JSONObject token = (JSONObject) access.get("token");

        return token.get("id").toString();
    }

}

