package shop.yesaladin.front.point.controller.web;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.common.dto.ResponseDto;
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
public class PointHistoryMyPageWebController {

    private final QueryPointHistoryService queryPointHistoryService;

    @GetMapping("/point-history")
    public String showPointHistory(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String code,
            Model model
    ) {
        ResponseDto<PaginatedResponseDto<PointHistoryResponseDto>> response =
                (Objects.isNull(code) || Objects.equals(code, ""))
                        ? queryPointHistoryService.getAllPointHistories(pageable)
                        : queryPointHistoryService.getPointHistories(pageable, code);

        model.addAttribute("currentPage", response.getData().getCurrentPage());
        model.addAttribute("totalPage", response.getData().getTotalPage());
        model.addAttribute("totalDataCount", response.getData().getTotalDataCount());
        model.addAttribute("dataList", response.getData().getDataList());

        return "mypage/point/point-history";
    }
}
