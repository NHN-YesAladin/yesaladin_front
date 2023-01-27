package shop.yesaladin.front.member.controller.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.member.dto.MemberGrade;
import shop.yesaladin.front.member.dto.MemberGradeHistoryResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberGradeHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.member.service.inter.CommandMemberService;

/**
 * 마이페이지 관련 회원 Controller입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MemberMyPageWebController {

    private final QueryMemberGradeHistoryService queryMemberGradeHistoryService;
    
    private final CommandMemberService commandMemberService;

    private final LocalDate DEFAULT_START_DATE = LocalDate.of(2023, 1, 1);

    /**
     * 마이페이지에서 회원의 등급 변경내역 페이지를 반환합니다.
     *
     * @param pageable       페이지와 사이즈
     * @param startDate      조회 시작일
     * @param endDate        조회 끝일
     * @param authentication 현재 로그인한 회원 정보
     * @param model          모델
     * @return 회원 등급 변경 내역 페이지
     */
    @GetMapping("/grade-history")
    public String gradeHistory(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Authentication authentication,
            Model model
    ) {
        String loginId = ((UserDetails) authentication.getPrincipal()).getUsername();

        startDate = Objects.isNull(startDate) ? DEFAULT_START_DATE : startDate;

        PaginatedResponseDto<MemberGradeHistoryResponseDto> response = queryMemberGradeHistoryService.getMemberGradeHsitoryHistories(
                pageable,
                loginId,
                startDate,
                endDate
        );

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());

        return "mypage/member/grade-history";
    }

    /**
     * 페이지 화면 테스트용입니다.
     *
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/grade-history/test")
    public String gradeHistoryTest(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model
    ) {
        int currentPage = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        int totalPage = 15;
        int totalDataCount = 149;
        List<MemberGradeHistoryResponseDto> dataList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Long amount = (random.nextLong() % 10 - 5) * 1000;
            dataList.add(new MemberGradeHistoryResponseDto(
                    (long) i,
                    LocalDate.now(),
                    amount,
                    MemberGrade.values()[(int) Math.random() * 4],
                    "test"
            ));
        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalDataCount", totalDataCount);
        model.addAttribute("dataList", dataList);

        return "mypage/member/grade-history";
    }
   

    @GetMapping("/withdraw")
    public String withdraw() {
        return "mypage/member/member-withdraw";
    }

    @PostMapping("/withdraw")
    public String doWithdraw() {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("loginId={}", loginId);
        commandMemberService.withdraw(loginId);

        return "redirect:/";
    }
}
