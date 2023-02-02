package shop.yesaladin.front.order.controller.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.order.dto.OrderSummaryResponseDto;
import shop.yesaladin.front.order.dto.TotalOrderResponseDto;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

/**
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage/orders")
public class OrderMyPageWebController {

    private final QueryOrderService queryOrderService;


    @GetMapping
    public String getOrderList(
            @RequestParam(name = "code", required = false) Integer code,
            @RequestParam(name = "endDate", required = false) String shouldEndDate,
            Pageable pageable,
            Model model
    ) {
        System.out.println("shouldEndDate = " + shouldEndDate);
        //TODO 사용자 인증
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        if (Objects.isNull(code)) {
            startDate = startDate.minusMonths(3);
        }else{
            startDate = endDate.minusDays(code);
        }

        PaginatedResponseDto<OrderSummaryResponseDto> response = queryOrderService.getOrderListInPeriodByMemberId(
                pageable,
                new PeriodQueryRequestDto(startDate, endDate)
        );

        log.info("{}",response.getDataList());

        model.addAttribute("code", code);
        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());

        return "mypage/order/order-list-view";
    }
}
