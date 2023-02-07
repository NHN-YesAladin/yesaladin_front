package shop.yesaladin.front.order.controller.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.common.dto.ResponseDto;
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
    @GetMapping("order-sheets")
    public String getOrderSheet(
            @RequestParam("type") String type,
            @RequestParam("isbn") List<String> isbn,
            @RequestParam("quantity") List<String> quantity,
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
        model.addAttribute("products", response.getData().getOrderProducts());
        model.addAttribute("name", response.getData().getName());
        model.addAttribute("phoneNumber", response.getData().getPhoneNumber());
        model.addAttribute("point", response.getData().getPoint());
        model.addAttribute("address", response.getData().getAddress());
        model.addAttribute("coupons", response.getData().getMemberCoupons());

        return (type.equals("subscribe")) ? "main/order/subscribe" : "main/order/order";
    }
}
