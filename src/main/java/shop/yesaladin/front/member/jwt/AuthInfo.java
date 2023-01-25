package shop.yesaladin.front.member.jwt;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import shop.yesaladin.front.member.dto.MemberResponse;

/**
 * user 정보와 accessToken을 Redis에 저장하여 관리하기 위한 클래스입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@ToString
public class AuthInfo implements Serializable {

    private final String name;
    private final String nickname;
    private final String loginId;
    private final String email;
    private final String password;
    private final String accessToken;
    private final List<SimpleGrantedAuthority> authorities;

    public AuthInfo(
            MemberResponse memberResponse,
            String accessToken,
            List<SimpleGrantedAuthority> authorities
    ) {
        this.name = memberResponse.getName();
        this.nickname = memberResponse.getNickname();
        this.loginId = memberResponse.getLoginId();
        this.email = memberResponse.getEmail();
        this.password = memberResponse.getPassword();
        this.accessToken = accessToken;
        this.authorities = authorities;
    }
}
