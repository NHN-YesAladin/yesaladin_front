package shop.yesaladin.front.member.controller.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.member.dto.MemberGrade;
import shop.yesaladin.front.member.dto.MemberGradeHistoryResponseDto;
import shop.yesaladin.front.member.dto.MemberQueryResponseDto;
import shop.yesaladin.front.member.dto.MemberUpdateRequestDto;
import shop.yesaladin.front.member.service.inter.CommandMemberService;
import shop.yesaladin.front.member.service.inter.QueryMemberGradeHistoryService;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 마이페이지 관련 회원 Controller입니다.
 *
 * @author 최예린, 송학현
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class MemberMyPageWebController {

    private final QueryMemberGradeHistoryService queryMemberGradeHistoryService;
    private final QueryMemberService queryMemberService;

    private final CommandMemberService commandMemberService;

    private final LocalDate DEFAULT_START_DATE = LocalDate.of(2023, 1, 1);

    /**
     * 마이페이지에서 회원의 등급 변경내역 페이지를 반환합니다.
     *
     * @param pageable  페이지와 사이즈
     * @param startDate 조회 시작일
     * @param endDate   조회 끝일
     * @param model     모델
     * @return 회원 등급 변경 내역 페이지
     */
    @GetMapping("/grade-history")
    public String gradeHistory(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model
    ) {
        startDate = Objects.isNull(startDate) ? DEFAULT_START_DATE : startDate;

        PaginatedResponseDto<MemberGradeHistoryResponseDto> response = queryMemberGradeHistoryService.getMemberGradeHsitoryHistories(
                pageable,
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
        int currentPage = pageable.getPageNumber();
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

    /**
     * 회원 정보 수정 페이지를 반환합니다.
     *
     * @param model 모델
     * @return 회원 정보 수정 페이지
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/edit")
    public String showEditPage(Model model) {
        MemberQueryResponseDto member = queryMemberService.getMemberInfo();

        model.addAttribute("nickname", member.getNickname());
        model.addAttribute("name", member.getName());
        model.addAttribute("loginId", member.getLoginId());
        model.addAttribute("password", member.getPassword());
        model.addAttribute(
                "birthDate",
                LocalDate.of(member.getBirthYear(), member.getBirthMonth(), member.getBirthDay())
        );
        model.addAttribute("email", member.getEmail());
        model.addAttribute("signUpDate", member.getSignUpDate());
        model.addAttribute("grade", member.getGrade());
        model.addAttribute("gender", member.getGender());

        return "mypage/member/edit";
    }

    @GetMapping("/edit/test")
    public String showTestEditPage(Model model) {
        model.addAttribute("nickname", "test");
        model.addAttribute("name", "test");
        model.addAttribute("loginId", "test@1");
        model.addAttribute("password", "1234");
        model.addAttribute("birthDate", LocalDate.now());
        model.addAttribute("email", "test@yesaladin.shop");
        model.addAttribute("signUpDate", LocalDate.now());
        model.addAttribute("grade", "화이트");
        model.addAttribute("gender", "남");

        return "mypage/member/edit";
    }

    /**
     * 회원의 정보를 수정합니다.
     *
     * @param request 수정할 회원 정보
     * @return 마이페이지 메일 화면
     * @author 최예린
     * @since 1.0
     */
    @PostMapping("/edit")
    public String editMemberInfo(@RequestBody MemberUpdateRequestDto request) {
        commandMemberService.edit(request);

        return "redirect:/mypage";
    }

    /**
     * 회원 탈퇴 페이지를 view로 리턴시켜주기 위한 Get handler 입니다.
     *
     * @return 회원 탈퇴 페이지
     * @author : 송학현
     * @since : 1.0
     */
    @GetMapping("/withdraw")
    public String withdraw() {
        return "mypage/member/member-withdraw";
    }

    /**
     * 회원 탈퇴를 위한 Post handler 입니다.
     *
     * @return 리다이렉트 된 메인 페이지
     * @author : 송학현
     * @since : 1.0
     */
    @PostMapping("/withdraw")
    public String doWithdraw() {
        SecurityContext context = SecurityContextHolder.getContext();
        String loginId = context.getAuthentication().getName();

        log.info("loginId={}", loginId);
        commandMemberService.withdraw(loginId);

        SecurityContextHolder.clearContext();
        context.setAuthentication(null);

        return "redirect:/";
    }
}
