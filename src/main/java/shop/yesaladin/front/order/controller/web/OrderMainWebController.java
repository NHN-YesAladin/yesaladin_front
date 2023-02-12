package shop.yesaladin.front.order.controller.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.order.dto.OrderDetailsResponseDto;
import shop.yesaladin.front.order.dto.OrderSheetResponseDto;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

/**
 * 메인페이지 주문 view 관련 controller 클래스 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
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

    @GetMapping("/{orderNumber}")
    public String getOrderDetails(@PathVariable String orderNumber, Model model) {
        OrderDetailsResponseDto detailsResponseDto = queryOrderService.getOrderDetailsDtoByOrderNumber(
                orderNumber);
        System.out.println("detailsResponseDto = " + detailsResponseDto);
        model.addAttribute("response", detailsResponseDto);
        return "main/order/order-details";
    }

    @GetMapping("/find-non-member-order")
    public String getNonMemberOrderFinder() {
        return "main/order/find-non-member-order";
    }
}
