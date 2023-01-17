package shop.yesaladin.front.member.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JWT의 Payload를 파싱하기 위한 클래스입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class JwtPayload implements Serializable {

    // JWT 발행인 (사용자 아이디)
    @JsonProperty("sub")
    private String sub;

    // 발행인의 권한 목록
    @JsonProperty("roles")
    private List<String> roles;

    // JWT 발행일
    @JsonProperty("iat")
    private Long iat;

    // JWT 만료일
    @JsonProperty("exp")
    private Long exp;
}
