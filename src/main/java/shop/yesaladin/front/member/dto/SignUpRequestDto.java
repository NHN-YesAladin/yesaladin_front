package shop.yesaladin.front.member.dto;

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
 * 회원 등록을 위해 MemberController 에서 받는 요청 DTO 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,50}$", message = "이름은 한글 또는 영어 2자에서 50자까지 입력 가능합니다.")
    private String name;

    @NotBlank
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$",
            message = "숫자, 영어, 한국어와 언더스코어를 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.")
    private String nickname;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "영문 또는 숫자로 8자 이상 15자 이하만 가능 합니다.")
    private String loginId;

    @Email(message = "이메일 양식을 지켜주세요.")
    @NotBlank(message = "email을 입력해주세요.")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^01([0|1])\\d{4}\\d{4}$", message = "휴대폰 번호를 - 없이 11자 입력해주세요. 앞 자리는 010 또는 011 양식입니다.")
    private String phone;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 최소 8자 최대 20자, 하나 이상의 문자와 하나의 숫자 및 하나의 특수 문자가 들어가야 합니다.")
    private String password;

    @NotBlank(message = "생년월일을 체크해 주세요.")
    private String birth;

    @NotBlank(message = "성별을 체크해 주세요.")
    private String gender;
}
