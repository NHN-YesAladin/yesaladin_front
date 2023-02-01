package shop.yesaladin.front.member.jwt;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.yesaladin.front.member.dto.MemberResponse;

/**
 * user 정보와 accessToken을 Redis에 저장하여 관리하기 위한 클래스입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfo implements Serializable {

    private Long id;
    private String name;
    private String nickname;
    private String loginId;
    private String email;
    private String accessToken;
    private List<String> authorities;

    public AuthInfo(
            MemberResponse memberResponse,
            String accessToken,
            List<String> authorities
    ) {
        this.id = memberResponse.getId();
        this.name = memberResponse.getName();
        this.nickname = memberResponse.getNickname();
        this.loginId = memberResponse.getLoginId();
        this.email = memberResponse.getEmail();
        this.accessToken = accessToken;
        this.authorities = authorities;
    }
}
