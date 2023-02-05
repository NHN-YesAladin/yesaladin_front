package shop.yesaladin.front.member.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멤버 차단 해지 이후 결과 DTO
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUnblockResponseDto {
    private Long id;
    private String name;
    private String loginId;
    private Boolean isBlocked;
    private LocalDate unblockedDate;
}
