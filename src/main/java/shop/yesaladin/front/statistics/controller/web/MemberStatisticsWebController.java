package shop.yesaladin.front.statistics.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;
import shop.yesaladin.front.statistics.dto.PercentageResponseDto;

/**
 * 관리자 페이지의 회원 통계 페이지에 관한 WebController 입니다.
 *
 * @author 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/manager")
public class MemberStatisticsWebController {

    private final QueryMemberService queryMemberService;

    /**
     * 관리자 페이지를 회원 통계 데이터와 함께 반환 합니다.
     *
     * @return 관리자페이지
     * @author 송학현
     * @since 1.0
     */
    @GetMapping
    public String manager(Model model) {
        MemberStatisticsResponseDto statistics = queryMemberService.getMemberStatistics();
        PercentageResponseDto percentage = PercentageResponseDto.calculatePercentages(statistics);
        statistics.setTotalMembers(calculateActualTotalMembers(statistics));

        model.addAttribute("statistics", statistics);
        model.addAttribute("percentages", percentage);

        return "manager/index";
    }

    /**
     * 실회원의 현황 수를 계산 합니다.
     *
     * @param dto 회원 통계 데이터
     * @return 전체 회원 - (탈퇴 + 차단)
     * @author 송학현
     * @since 1.0
     */
    private Long calculateActualTotalMembers(MemberStatisticsResponseDto dto) {
        return dto.getTotalMembers() - (dto.getTotalBlockedMembers()
                + dto.getTotalWithdrawMembers());
    }
}
