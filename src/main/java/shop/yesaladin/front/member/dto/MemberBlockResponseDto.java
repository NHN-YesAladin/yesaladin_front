package shop.yesaladin.front.member.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 차단 후 결과 DTO
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberBlockResponseDto {

    private Long id;
    private String name;
    private String loginId;
    private Boolean isBlocked;
    private LocalDate blockedDate;
    private String blockedReason;
}
