package shop.yesaladin.front.oauth.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * OAuth2 Login 기능을 수행하기 위한 adapter 클래스 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class Oauth2Adapter {

    private final RestTemplate restTemplate;

    /**
     * OAuth2에서 accessToken을 발급 받기 위해 요청을 수행하는 기능 입니다.
     *
     * @param url accessToken을 발급 받기 위한 경로 입니다.
     * @return OAuth2에서 인증된 사용자임을 나타내는 accessToken
     * @author 송학현
     * @since 1.0
     */
    public ResponseEntity<String> getAccessToken(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
    }

    /**
     * OAuth2에 사용자 정보 요청을 수행하는 기능입니다.
     *
     * @param token OAuth2에서 발급 받은 accessToken
     * @param userInfoProcessingUrl OAuth2에서 제공하는 사용자 정보 요청 url
     * @return 사용자 정보
     * @author 송학현
     * @since 1.0
     */
    public ResponseEntity<String> getUser(String token, String userInfoProcessingUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        return restTemplate.exchange(
                userInfoProcessingUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
    }
}
