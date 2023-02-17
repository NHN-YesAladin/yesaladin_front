package shop.yesaladin.front.oauth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * OAuth2 로그인 시 회원 등록을 위해 MemberAuthWebController 에서 받는 요청 DTO 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2SignUpRequestDto {

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,50}$", message = "이름은 한글 또는 영어 2자에서 50자까지 입력 가능합니다.")
    private String name;

    @NotBlank
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$",
            message = "숫자, 영어, 한국어와 언더스코어, 공백을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.")
    private String nickname;

    @NotBlank
    @Size(min = 1, max = 50)
    private String loginId;

    @Email(message = "이메일 양식을 지켜주세요.")
    @NotBlank(message = "email을 입력해주세요.")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(min = 11, max = 11, message = "- 없이 11자 입력해주세요.")
    private String phone;

    @NotBlank
    private String password;

    @NotBlank(message = "생년월일을 체크해 주세요.")
    private String birth;

    @NotBlank(message = "성별을 체크해 주세요.")
    private String gender;
}
