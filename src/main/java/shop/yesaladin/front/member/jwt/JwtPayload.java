package shop.yesaladin.front.member.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
