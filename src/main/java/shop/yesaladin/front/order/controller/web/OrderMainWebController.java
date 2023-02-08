package shop.yesaladin.front.order.controller.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;
import shop.yesaladin.front.order.dto.OrderNonMemberCreateRequestDto;
import shop.yesaladin.front.order.dto.OrderSheetResponseDto;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

/**
 * 메인페이지 주문 view 관련 controller 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderMainWebController {

    private final QueryOrderService queryOrderService;

    /**
     * 주문할 상품들의 데이터를 받아 주문서 view 를 리턴합니다.
     *
     * @param model 뷰
     * @return 주문서 view
     * @author 최예린
     * @since 1.0
     */
    @GetMapping(path = {"/order-sheets", "/subscribe-sheets"})
    public String getOrderSheet(
            @RequestParam("isbn") List<String> isbn,
            @RequestParam("quantity") List<String> quantity,
            HttpServletRequest request,
            Model model
    ) {
        ResponseDto<OrderSheetResponseDto> response = queryOrderService.getOrderSheetData(
                isbn,
                quantity
        );

        if (!response.isSuccess()) {
            model.addAttribute("error", response.getErrorMessages());
            return "common/errors/error";
        }
        model.addAttribute("info", response.getData());

        return (request.getServletPath().contains("subscribe")) ? "main/order/subscribe"
                : "main/order/order";
    }

    @PostMapping("/member")
    public String createMemberOrder(
            @Valid @ModelAttribute OrderMemberCreateRequestDto request,
            Model model
    ) {
        log.error("request : {}", request);
        return "/cart/cart";
    }
    @PostMapping("/non-member")
    public String createNonMemberOrder(
            @Valid @ModelAttribute OrderNonMemberCreateRequestDto request,
            Model model
    ) {
        log.error("request : {}", request);
        return "/cart/cart";
    }
}
