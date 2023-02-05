package shop.yesaladin.front.member.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자의 회원 관리 페이징 처리를 위한 dto
 *
 * @author 김선홍
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberManagerListResponseDto {
    private Long count;
    private List<MemberManagerResponseDto> memberManagerResponseDtoList;
}
