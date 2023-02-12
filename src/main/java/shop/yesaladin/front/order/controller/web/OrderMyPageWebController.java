package shop.yesaladin.front.order.controller.web;

import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.order.dto.OrderStatusCode;
import shop.yesaladin.front.order.dto.OrderStatusResponseDto;
import shop.yesaladin.front.order.dto.OrderSummaryResponseDto;
import shop.yesaladin.front.order.service.inter.CommandOrderService;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

/**
 * 주문 조회를 view 연결해주는 컨트롤러
 *
 * @author 배수한
 * @since 1.0
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage")
public class OrderMyPageWebController {

    private final QueryOrderService queryOrderService;
    private final CommandOrderService commandOrderService;

    /**
     * 전체 주문 조회 화면 연결 메서드
     *
     * @param code          조회 필요 일자
     * @param shouldEndDate 달력 사용시, 원하는 마지막날을 지정
     * @param pageable      페이징 처리용
     * @param model         view에서 사용
     * @return
     */
    @GetMapping("/orders")
    public String getOrderList(
            @RequestParam(name = "code", required = false) Integer code,
            @RequestParam(name = "endDate", required = false) String shouldEndDate,
            Pageable pageable,
            Model model
    ) {
        //TODO 사용자 인증
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        // 달력으로 날짜 지정시 마지막 조회 날짜 변경하기 위함
        if (Objects.nonNull(shouldEndDate)) {
            endDate = LocalDate.parse(shouldEndDate);
        }

        // code가 null인 경우 : 3개월치 조회
        // code가 null이 아닌 경우 경우 : 일자에 맞춰 조회
        if (Objects.isNull(code)) {
            startDate = startDate.minusMonths(3);
        } else {
            startDate = endDate.minusDays(code);
        }

        PaginatedResponseDto<OrderSummaryResponseDto> response = queryOrderService.getOrderListInPeriodByMemberId(
                pageable,
                new PeriodQueryRequestDto(startDate, endDate)
        );

        model.addAttribute("code", code);
        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());

        return "mypage/order/order-list-view";
    }

    /**
     * 주문 상태에 따른 주문 조회
     *
     * @param status   주문 상태 코드의 숫자값
     * @param pageable 페이징 처리용
     * @param model    모델
     * @return 주문 팝업창 위치
     */
    @GetMapping(value = "/order-popup", params = "code")
    public String getMyPageOrderPopup(
            @RequestParam("code") Long status,
            Pageable pageable,
            Model model
    ) {
        OrderStatusCode code = OrderStatusCode.getOrderStatusCodeByNumber(status);
        PaginatedResponseDto<OrderStatusResponseDto> response = queryOrderService.getOrderListByOrderStatus(
                pageable,
                status
        );

        log.info(
                "getTotalPage : {} | getTotalDataCount : {}",
                response.getTotalPage(),
                response.getTotalDataCount()
        );

        model.addAttribute("code", code.getStatusCode());
        model.addAttribute("title", code.getKoName());
        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());
        return "mypage/order/my-order-popup";
    }

    /**
     * 회원의 주문을 숨김처리합니다.
     *
     * @param orderId 숨길 주문 pk
     * @param hidden  숨김 여부
     * @return 숨김 성공 여부
     * @author 최예린
     * @since 1.0
     */
    @GetMapping(path = "/orders/{orderId}", params = "hidden")
    public String hideOrder(@PathVariable Long orderId, @RequestParam Boolean hidden) {
        commandOrderService.hideOrder(orderId, hidden);

        return "redirect:/mypage/orders";
    }
}
