package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 등록 이후 MemberController 에서 클라이언트 에게 반환하기 위한 결과 DTO 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {

    private Long id;
    private String name;
    private String nickname;
    private String loginId;
    private MemberGradeResponse memberGrade;
    private String memberRole;
}
