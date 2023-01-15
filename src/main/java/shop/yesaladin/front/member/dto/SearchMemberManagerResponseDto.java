package shop.yesaladin.front.member.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 검색한 회원의 정보
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchMemberManagerResponseDto {

    private Long id;
    @NotBlank
    private String nickname;
    @NotBlank
    private String name;
    @NotBlank
    private String loginId;
    @NotBlank
    private String phone;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    @Email
    private String email;
    private LocalDate signUpDate;
    private LocalDate withdrawalDate;
    private boolean isWithdrawal;
    private boolean isBlocked;
    private Long point;
    private String grade;
    private String gender;
}
