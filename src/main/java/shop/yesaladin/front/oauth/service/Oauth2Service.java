package shop.yesaladin.front.oauth.service;

import static shop.yesaladin.front.oauth.util.Oauth2Utils.ACCESS_TOKEN;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import shop.yesaladin.front.oauth.adapter.Oauth2Adapter;
import shop.yesaladin.front.oauth.dto.Oauth2LoginRequestDto;
import shop.yesaladin.front.oauth.exception.Oauth2ParseProcessingException;

/**
 * OAuth2 Login Service의 추상 클래스 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class Oauth2Service {

    protected final Oauth2Adapter oauth2Adapter;
    protected final ObjectMapper objectMapper;

    /**
     * OAuth2 Login 수행 경로를 반환 하기 위한 기능입니다.
     *
     * @return OAuth2 Login 수행 경로
     * @author 송학현
     * @since 1.0
     */
    public abstract String getOAuth2ProcessingUrl();

    /**
     * OAuth2에서 발급 받은 code를 통해 accessToken을 가져오기 위한 수행 경로를 반환하는 기능입니다.
     *
     * @param code OAuth2에서 발급 받은 code
     * @return OAuth2에서 인증된 사용자임을 나타내는 accessToken을 반환하기 위한 수행 경로
     * @author 송학현
     * @since 1.0
     */
    public abstract String tokenProcessingUrl(String code);

    /**
     * OAuth2에 사용자 정보를 요청하기 위한 경로를 반환 하기 위한 기능입니다.
     *
     * @return OAuth2에서 제공 하는 사용자 정보 요청 url
     * @author 송학현
     * @since 1.0
     */
    public abstract String getUserInfoProcessingUrl();

    /**
     * OAuth2에서 제공 받은 사용자 정보를 바탕으로 Login 처리를 위한 DTO 클래스를 만들기 위한 기능입니다. 해당 사용자가 OAuth2 서비스에 등록된
     * email이 없는 경우, email을 랜덤으로 생성합니다.
     *
     * @param userInfo OAuth2에서 제공 받은 사용자 정보 Map
     * @return Login 처리를 위해 변환된 DTO 클래스
     * @author 송학현
     * @since 1.0
     */
    public abstract Oauth2LoginRequestDto createOauth2Dto(Map<String, Object> userInfo);

    /**
     * OAuth2에서 accessToken을 발급 받기 위한 기능입니다.
     *
     * @param tokenProcessingUrl accessToken을 발급 받기 위한 경로 입니다.
     * @return OAuth2에서 인증된 사용자임을 나타내는 accessToken
     * @author 송학현
     * @since 1.0
     */
    public String getAccessToken(String tokenProcessingUrl) {
        ResponseEntity<String> response = oauth2Adapter.getAccessToken(tokenProcessingUrl);

        Map accessToken;

        try {
            accessToken = objectMapper.readValue(response.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new Oauth2ParseProcessingException();
        }

        return (String) accessToken.get(ACCESS_TOKEN.getValue());
    }

    /**
     * OAuth2에 사용자 정보를 요청하기 위한 기능입니다.
     *
     * @param token                 code를 통해 OAuth2에서 발급 받은 accessToken
     * @param userInfoProcessingUrl OAuth2에서 제공 하는 사용자 정보 요청 url
     * @return 사용자 정보
     * @author 송학현
     * @since 1.0
     */
    public Map<String, Object> getUserInfo(String token, String userInfoProcessingUrl) {
        Map<String, Object> user;

        try {
            ResponseEntity<String> oauth2User = oauth2Adapter.getUser(token, userInfoProcessingUrl);
            user = objectMapper.readValue(oauth2User.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new Oauth2ParseProcessingException();
        }

        return user;
    }

    /**
     * OAuth2 로그인시 YesAladin 자사 회원인지 판별하기 위한 기능입니다.
     *
     * @param dto OAuth Login을 위한 dto
     * @return 해당 유저가 YesAladin 자사 회원 인지에 대한 결과
     * @author 송학현
     * @since 1.0
     */
    public boolean isAlreadyMember(Oauth2LoginRequestDto dto) {
        return oauth2Adapter.isAlreadyMember(dto.getLoginId());
    }
}
