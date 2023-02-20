package shop.yesaladin.front.order.controller.web;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.order.dto.*;
import shop.yesaladin.front.order.service.inter.CommandOrderService;
import shop.yesaladin.front.order.service.inter.QueryOrderService;
import shop.yesaladin.front.payment.dto.PaymentViewRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private final CommandOrderService commandOrderService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtils cookieUtils;

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


    @GetMapping(value = "/{orderNumber}")
    public String getOrderDetails(
            @PathVariable String orderNumber,
            @RequestParam(value = "type", required = false) String type,
            Model model
    ) {
        OrderDetailsResponseDto detailsResponseDto = queryOrderService.getOrderDetailsDtoByOrderNumber(
                orderNumber, type);
        model.addAttribute("response", detailsResponseDto);
        return "main/order/order-details";
    }

    @GetMapping("/find-non-member-order")
    public String getNonMemberOrderFinder() {
        return "main/order/find-non-member-order";
    }

    /**
     * 회원의 주문을 생성합니다.
     *
     * @param request 회원의 주문 생성에 필요한 데이터
     * @return 생성된 회원의 주문
     * @author 최예린
     * @since 1.0
     */
    @PostMapping("/member")
    public String createMemberOrder(
            @ModelAttribute OrderMemberRequestDto request,
            @CookieValue(value = "CART_NO", required = false) Cookie cookie,
            HttpServletResponse httpServletResponse,
            Model model
    ) {

        ResponseDto<OrderCreateResponseDto> response = commandOrderService.createMemberOrder(request.toOrderMemberCreateRequest());

        String orderName = response.getData().getOrderName();
        String orderNumber = response.getData().getOrderNumber();

        PaymentViewRequestDto payRequest = request.toPaymentViewRequest(orderNumber, orderName);
        model.addAttribute("data", payRequest);

        // 장바구니 flush
        cookieUtils.deleteCart(redisTemplate, cookie, httpServletResponse);

        return "main/payment/pay-page";

    }

    /**
     * 주문 & 결제 성공시 보여주는 완료 페이지
     *
     * @param orderNumber 주문 번호
     * @param model       모델
     * @return 결제 완료 페이지
     */
    @GetMapping(value = "/order-complete", params = "orderNumber")
    public String getOrderComplete(
            @RequestParam(name = "orderNumber") String orderNumber,
            Model model
    ) {
        model.addAttribute("orderNumber", orderNumber);
        return "main/order/order-complete";
    }


    /**
     * 주문 & 결제 실패시 보여주는 실패 페이지
     *
     * @param requestDto 주문번호, 주문이름, 총 결제액
     * @param model      모델
     * @return 결제 실패 페이지
     */
    @GetMapping(value = "/order-fail", params = "orderNumber")
    public String getOrderFail(@ModelAttribute OrderPaymentRequestDto requestDto, Model model) {
        model.addAttribute("orderNumber", requestDto.getOrderNumber());
        model.addAttribute("orderName", requestDto.getOrderName());
        model.addAttribute("amount", requestDto.getTotalAmount());
        return "main/order/order-fail";
    }
}
