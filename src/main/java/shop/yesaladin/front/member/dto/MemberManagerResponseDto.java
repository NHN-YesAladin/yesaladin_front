package shop.yesaladin.front.member.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자의 회원 관리 Response Dto
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberManagerResponseDto {
    private Long id;
    private String nickname;
    private String loginId;
    private String email;
    private String phone;
    private String name;
    private LocalDate signUpDate;
    private LocalDate withdrawalDate;
    private Boolean isWithdrawal;
    private Boolean isBlocked;
    private String blockedReason;
    private LocalDate blockedDate;
    private LocalDate unblockedDate;
}
