package shop.yesaladin.front.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * OAuth2 Login 시 사용하는 DTO 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2LoginRequestDto {

    private String loginId;
    private String password;
}
