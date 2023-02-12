package shop.yesaladin.front.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 현황 통계 정보를 담은 DTO 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberStatisticsResponseDto {

    private Long totalMembers;
    private Long totalBlockedMembers;
    private Long totalWithdrawMembers;

    private Long totalWhiteGrades;
    private Long totalBronzeGrades;
    private Long totalSilverGrades;
    private Long totalGoldGrades;
    private Long totalPlatinumGrades;
}
