package shop.yesaladin.front.member.jwt;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.yesaladin.front.member.dto.MemberResponseDto;

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
            MemberResponseDto memberResponseDto,
            String accessToken,
            List<String> authorities
    ) {
        this.id = memberResponseDto.getId();
        this.name = memberResponseDto.getName();
        this.nickname = memberResponseDto.getNickname();
        this.loginId = memberResponseDto.getLoginId();
        this.email = memberResponseDto.getEmail();
        this.accessToken = accessToken;
        this.authorities = authorities;
    }
}
