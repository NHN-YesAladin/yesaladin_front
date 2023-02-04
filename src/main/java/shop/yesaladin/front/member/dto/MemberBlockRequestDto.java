package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 차단 request DTO
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberBlockRequestDto {
    private String blockedReason;
}
