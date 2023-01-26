package shop.yesaladin.front.point.controller.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.point.dto.PointHistoryResponseDto;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;

/**
 * 마이페이지관련 포인트 컨트롤러입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class PointHistoryMemberWebController {

    private final QueryPointHistoryService queryPointHistoryService;

    @GetMapping("/point-history")
    public String showPointHistory(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String code,
            Authentication authentication,
            Model model
    ) {
        String loginId = ((UserDetails) authentication.getPrincipal()).getUsername();

        PaginatedResponseDto<PointHistoryResponseDto> response =
                (Objects.isNull(code) || Objects.equals(code, "")) ? queryPointHistoryService.getAllPointHistories(pageable, loginId)
                        : queryPointHistoryService.getPointHistories(pageable, loginId, code);

        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());

        return "mypage/page/point-history";
    }

    @GetMapping("/point-history/test")
    public String test(Model model, @RequestParam(required = false) String code, @PageableDefault Pageable pageable) {
        int currentPage = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        int totalPage = 15;
        int totalDataCount = 149;
        List<PointHistoryResponseDto> dataList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Long amount = (random.nextLong() % 10 - 5) * 1000;
            dataList.add(new PointHistoryResponseDto(
                    (long) i,
                    amount,
                    LocalDateTime.now(),
                    amount < 0 ? "USE" : "SAVE",
                    "test"
            ));
        }

        model.addAttribute("code", code);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalDataCount", totalDataCount);
        model.addAttribute("dataList", dataList);
        return "mypage/page/point-history";
    }

}
