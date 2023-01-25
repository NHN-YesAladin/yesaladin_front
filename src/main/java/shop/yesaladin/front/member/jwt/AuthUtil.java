package shop.yesaladin.front.member.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Redis와 Cookie에 사용할 key 값을 관리 하기 위한 Enum 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@RequiredArgsConstructor
public enum AuthUtil {
    JWT_CODE("JWT"),
    UUID_CODE("UUID");

    private final String value;
}
