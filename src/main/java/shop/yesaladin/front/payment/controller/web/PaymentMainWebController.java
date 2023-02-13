package shop.yesaladin.front.payment.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.order.dto.OrderPaymentRequestDto;
import shop.yesaladin.front.order.dto.OrderStatusResponseDto;
import shop.yesaladin.front.payment.dto.PaymentCompleteSimpleResponseDto;
import shop.yesaladin.front.payment.dto.PaymentRequestDto;
import shop.yesaladin.front.payment.dto.PaymentViewRequestDto;
import shop.yesaladin.front.payment.service.inter.PaymentService;

/**
 * 결제를 처리하는 컨트롤러
 *
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/payments")
public class PaymentMainWebController {

    private final PaymentService paymentService;

    /**
     * 주문 이후에 결제 화면으로 넘기기 위해 사용
     *
     * @param model 모델
     * @return 결제 페이지
     */
    @GetMapping("/pay")
    public String getPayPage(@ModelAttribute PaymentViewRequestDto request, Model model) {
        model.addAttribute("data", request);
        return "main/payment/pay-page";
    }

    /**
     * 팝업창에서 다시 결제를 할때, 팝업창에서 결제창이 띄어지지 않게 새창에서 결제 시키기 위해 사용
     *
     * @param requestDto 결제 승인을 위한 dto
     * @param model      모델
     * @return 결제창만 뜨는 화면
     */
    @GetMapping("/empty-pay")
    public String getEmptyPage(@ModelAttribute OrderPaymentRequestDto requestDto, Model model) {
        model.addAttribute("data", requestDto);
        return "mypage/payment/empty-pay";
    }

    /**
     * 토스 페이먼츠의 결제 중 결제 승인 시퀀스를 처리하기 위해 successUrl로 지정된 컨트롤러 메서드
     *
     * @param requestDto 결제에 필요한 필수 정보들
     * @return 성공시 주문 완성 페이지, 실패시 주문 실패 페이지
     */
    @GetMapping("/order-pay")
    public String pay(
            @ModelAttribute PaymentRequestDto requestDto
    ) {

        ResponseDto<PaymentCompleteSimpleResponseDto> confirmResponse = paymentService.confirm(
                requestDto);
        PaymentCompleteSimpleResponseDto responseDto = confirmResponse.getData();
        log.info("confirm : {}", responseDto);

        String orderNum = requestDto.getOrderId();
        if (!confirmResponse.isSuccess()) {
            StringBuilder builder = new StringBuilder("redirect:/orders/order-fail?");
            builder.append("orderNumber=")
                    .append(orderNum)
                    .append("&orderName=")
                    .append(responseDto.getOrdererName())
                    .append("&totalAmount=")
                    .append(responseDto.getTotalAmount());
            return builder.toString();
        }
        return "redirect:/orders/order-complete?orderNumber="+ orderNum;
    }


    /**
     * 결제 실패시 동작하는 메서드
     *
     * @param message    실패 메시지
     * @param code       실패 코드
     * @param requestDto 바로 결제시도를 위한 정보를 담는 dto
     * @param model      모델
     * @return 결제 실패 페이지
     */
    @RequestMapping("/fail")
    public String failPayment(
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String code,
            @ModelAttribute PaymentRequestDto requestDto,
            Model model
    ) {

        if (code.equals("PAY_PROCESS_ABORTED")) {
            log.error("PAY_PROCESS_ABORTED : {}", message);
            model.addAttribute("error", message);
            return "common/errors/4xx";
        }

        OrderStatusResponseDto responseDto = OrderStatusResponseDto.builder()
                .orderName("결제 실패로 재결제")
                .orderNumber(requestDto.getOrderId())
                .totalAmount(requestDto.getAmount())
                .build();

        log.info("{} / {}", code, message);
        log.info(
                "{} / {} / {}",
                responseDto.getOrderId(),
                responseDto.getOrderName(),
                responseDto.getTotalAmount()
        );
        StringBuilder builder = new StringBuilder("redirect:/orders/order-fail?");
        builder.append("orderNumber=")
                .append(responseDto.getOrderId())
                .append("&orderName=")
                .append(responseDto.getOrderName())
                .append("&totalAmount=")
                .append(responseDto.getTotalAmount());
        return builder.toString();
    }
}

