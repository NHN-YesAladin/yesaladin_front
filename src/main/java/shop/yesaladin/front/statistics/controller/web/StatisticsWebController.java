package shop.yesaladin.front.statistics.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;
import shop.yesaladin.front.order.service.inter.QueryOrderService;
import shop.yesaladin.front.statistics.dto.PercentageResponseDto;
import shop.yesaladin.front.statistics.dto.SalesStatisticsResponseDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/**
 * 관리자 페이지의 통계 페이지에 관한 WebController 입니다.
 *
 * @author 송학현
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/manager")
public class StatisticsWebController {

    private final QueryMemberService queryMemberService;
    private final QueryOrderService queryOrderService;

    /**
     * 관리자 페이지를 회원 통계 데이터와 함께 반환 합니다.
     *
     * @return 관리자페이지
     * @author 송학현
     * @author 이수정
     * @since 1.0
     */
    @GetMapping
    public String manager(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            Model model
    ) {
        MemberStatisticsResponseDto statistics = queryMemberService.getMemberStatistics();
        PercentageResponseDto percentage = PercentageResponseDto.calculatePercentages(statistics);
        statistics.setTotalMembers(calculateActualTotalMembers(statistics));

        model.addAttribute("statistics", statistics);
        model.addAttribute("percentages", percentage);

        if (Objects.isNull(start) || Objects.isNull(end)) {
            start = LocalDate.now().toString();
            end = LocalDate.now().toString();
        }
        PaginatedResponseDto<SalesStatisticsResponseDto> salesStatistics = queryOrderService.getSalesStatistics(
                PageRequest.of(page, size),
                start,
                end
        );

        Map<String, Object> pageInfoMap = getPageInfo(size, salesStatistics);
        model.addAllAttributes(pageInfoMap);

        model.addAttribute("start", start);
        model.addAttribute("end", end);

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

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param salesStatistics 페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            Integer size,
            PaginatedResponseDto<SalesStatisticsResponseDto> salesStatistics
    ) {
        return Map.of(
                "size", size,
                "totalPage", salesStatistics.getTotalPage(),
                "currentPage", salesStatistics.getCurrentPage(),
                "totalDataCount", salesStatistics.getTotalDataCount(),
                "salesStatistics", salesStatistics.getDataList()
        );
    }
}
