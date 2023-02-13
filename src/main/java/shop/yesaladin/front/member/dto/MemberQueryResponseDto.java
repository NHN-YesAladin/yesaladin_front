package shop.yesaladin.front.member.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원의 정보를 반환하는 dto 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberQueryResponseDto {

    private Long id;
    private String nickname;
    private String name;
    private String loginId;
    private String password;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;
    private String email;
    private LocalDate signUpDate;
    private String grade;
    private String gender;
}
