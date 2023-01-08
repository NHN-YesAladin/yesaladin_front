package shop.yesaladin.front.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 등록을 위해 MemberController 에서 받는 요청 DTO 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,15}$", message = "한글과 영문만 가능 합니다")
    private String nickname;

    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]*$", message = "영문(필수)과 숫자(옵션) 순서 로만 가능 합니다")
    private String loginId;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 11, max = 11)
    private String phone;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "최소 8자, 하나 이상의 문자와 하나의 숫자 및 하나의 특수 문자")
    private String password;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "최소 8자, 하나 이상의 문자와 하나의 숫자 및 하나의 특수 문자")
    private String passwordCheck;

    @NotBlank
    @Size(min = 8, max = 8)
    // TODO: 정규식 수정할 것
    @Pattern(regexp = "^[0-9]{8}")
    private String birth;

    @NotBlank
    private String gender;
}
