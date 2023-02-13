package shop.yesaladin.front.statistics.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;

/**
 * 통게 정보의 비율을 나타 내는 DTO 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PercentageResponseDto {

    private String members;
    private String withdraws;
    private String blocked;
    private String white;
    private String bronze;
    private String silver;
    private String gold;
    private String platinum;

    /**
     * 통게 정보의 비율 계산 후 DTO로 반환 하기 위한 기능 입니다.
     *
     * @param dto 통계 API 결과로 받아온 회원 통계 데이터
     * @return 비율 계산 후 데이터를 담은 결과
     * @author 송학현
     * @since 1.0
     */
    public static PercentageResponseDto calculatePercentages(MemberStatisticsResponseDto dto) {
        double members = getPercentage(dto.getTotalMembers() - (dto.getTotalBlockedMembers()
                + dto.getTotalWithdrawMembers()), dto.getTotalMembers());
        double blocked = getPercentage(dto.getTotalBlockedMembers(), dto.getTotalMembers());
        double withdraws = getPercentage(dto.getTotalWithdrawMembers(), dto.getTotalMembers());
        double white = getPercentage(dto.getTotalWhiteGrades(), dto.getTotalMembers());
        double bronze = getPercentage(dto.getTotalBronzeGrades(), dto.getTotalMembers());
        double silver = getPercentage(dto.getTotalSilverGrades(), dto.getTotalMembers());
        double gold = getPercentage(dto.getTotalGoldGrades(), dto.getTotalMembers());
        double platinum = getPercentage(dto.getTotalPlatinumGrades(), dto.getTotalMembers());

        return PercentageResponseDto.builder()
                .members(String.format("%.2f", members))
                .blocked(String.format("%.2f", blocked))
                .withdraws(String.format("%.2f", withdraws))
                .white(String.format("%.2f", white))
                .bronze(String.format("%.2f", bronze))
                .silver(String.format("%.2f", silver))
                .gold(String.format("%.2f", gold))
                .platinum(String.format("%.2f", platinum))
                .build();
    }

    private static double getPercentage(Long target, Long base) {
        int totalPercentage = 100;
        return ((double) target / (double) base) * totalPercentage;
    }
}
