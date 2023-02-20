package shop.yesaladin.front.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원의 이메일을 수정하기 위한 DTO 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEmailUpdateRequestDto {

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
}
