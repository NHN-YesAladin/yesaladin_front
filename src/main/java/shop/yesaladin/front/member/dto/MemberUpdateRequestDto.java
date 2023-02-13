package shop.yesaladin.front.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 정보 수정을 위해 MemberController 에서 받는 요청 DTO 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequestDto {

    private String nickname;
}
