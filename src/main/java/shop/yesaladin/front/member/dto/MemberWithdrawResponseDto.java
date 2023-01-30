package shop.yesaladin.front.member.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원 탈퇴 결과에 대한 Response Dto 입니다.
 *
 * @author : 송학현
 * @since : 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithdrawResponseDto {

    private Long id;
    private String name;
    private boolean withdrawal;
    private LocalDate withdrawalDate;
}
