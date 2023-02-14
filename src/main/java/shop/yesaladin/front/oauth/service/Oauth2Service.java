package shop.yesaladin.front.oauth.service;

import static shop.yesaladin.front.oauth.util.Oauth2Utils.ACCESS_TOKEN;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.EMAIL;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.GITHUB;
import static shop.yesaladin.front.oauth.util.Oauth2Utils.KAKAO_ACCOUNT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import shop.yesaladin.front.member.dto.MemberResponseDto;
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
     * @param userInfo OAuth2에서 제공 받은 사용자 정보
     * @param provider OAuth2 provider의 종류 입니다.
     * @return 해당 유저가 YesAladin 자사 회원 인지에 대한 결과
     * @author 송학현
     * @since 1.0
     */
    public boolean isAlreadyMember(Map<String, Object> userInfo, String provider) {
        String loginId;
        if (provider.equals(GITHUB.getValue())) {
            loginId = Objects.nonNull(userInfo.get(EMAIL.getValue())) ? userInfo.get(EMAIL.getValue())
                    .toString().split("@")[0] : userInfo.get("login").toString();
            return oauth2Adapter.isAlreadyMember(loginId);
        } else { // KAKAO 인 경우
            Map<String, String> kakaoAccount = (Map) userInfo.get(KAKAO_ACCOUNT.getValue());
            loginId = Objects.nonNull(kakaoAccount.get(EMAIL.getValue()))
                    ? kakaoAccount.get(EMAIL.getValue()).split("@")[0]
                    : userInfo.get("id").toString();
            return oauth2Adapter.isAlreadyMember(loginId);
        }
    }

    /**
     * OAuth2 로그인 시 YesAladin 자사 회원을 loginId 기준으로 불러오기 위한 기능입니다.
     *
     * @param loginId OAuth2에서 제공 받은 회원의 정보로 생성된 loginId 입니다.
     * @return loginId를 기준으로 Shop API 서버에서 제공 받은 YesAladin 회원 정보
     * @author 송학현
     * @since 1.0
     */
    public MemberResponseDto getMember(String loginId) {
        return Objects.requireNonNull(oauth2Adapter.getYesaladinMember(loginId).getBody())
                .getData();
    }
}
