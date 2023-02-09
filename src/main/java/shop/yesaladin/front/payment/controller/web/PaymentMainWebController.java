package shop.yesaladin.front.payment.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.common.code.ErrorCode;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.common.exception.ClientException;
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
    public String getPayPage(Model model) {

        //TODO 주문에서 RedirectAttribute 로 GET 요청으로 넘겨줄 예정 -> model로 받아서 적용시키면됨
        PaymentViewRequestDto viewDto = PaymentViewRequestDto.builder()
                .ordererName("김박사")
                .ordererPhoneNumber("01099992023")
                .receiverName("박박사")
                .receiverPhoneNumber("01022223333")
                .receiverAddress("광주 광역시광주 광역시광주 광역시광주 광역시광주 광역시광주 광역시광주 광역시")
                .receiverExpectedDate("2023-02-07")
                .orderNumber("123fwaf123wqsadsa")
                .productAmount(1000000L)
                .discountAmount(100000L)
                .shippingFee(3000L)
                .wrappingFee(3000L)
                .orderName("주문주문주문주문")
                .totalAmount(1000000L - 100000L + 3000L + 3000L)
                .build();

        model.addAttribute("data", viewDto);

        return "main/payment/pay-page";
    }

    /**
     * 팝업창에서 다시 결제를 할때, 팝업창에서 결제창이 띄어지지 않게 새창에서 결제 시키기 위해 사용
     *
     * @param requestDto 결제 승인을 위한 dto
     * @param model 모델
     * @return 결제창만 뜨는 화면
     */
    @GetMapping("/empty-pay")
    public String getEmptyPage(@ModelAttribute OrderPaymentRequestDto requestDto, Model model) {
        System.out.println("requestDto = " + requestDto);
        model.addAttribute("data", requestDto);
        return "main/payment/empty-pay";
    }

    /**
     * 토스 페이먼츠의 결제 중 결제 승인 시퀀스를 처리하기 위해 successUrl로 지정된 컨트롤러 메서드
     *
     * @param requestDto 결제에 필요한 필수 정보들
     * @param model
     * @return 성공시 주문 완성 페이지, 실패시 주문 실패 페이지
     */
    @GetMapping("/order-pay")
    public String pay(
            @ModelAttribute PaymentRequestDto requestDto,
            Model model
    ) {
        ResponseDto<PaymentCompleteSimpleResponseDto> confirmResponse = paymentService.confirm(
                requestDto);
        PaymentCompleteSimpleResponseDto responseDto = confirmResponse.getData();
        log.info("confirm : {}", responseDto);

        model.addAttribute("response", responseDto);

        if (!confirmResponse.isSuccess()) {
            model.addAttribute("isAfterOrder", true);
            return "main/order/order-fail";
        }

        return "main/order/order-complete";
    }


    /**
     * 결제 실패시 동작하는 메서드
     *
     * @param message 실패 메시지
     * @param code 실패 코드
     * @param requestDto 바로 결제시도를 위한 정보를 담는 dto
     * @param model 모델
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
            return "common/errors/4xx";
        }

        OrderStatusResponseDto responseDto = OrderStatusResponseDto.builder()
                .orderName("Retry Pay")
                .orderNumber(requestDto.getOrderId())
                .totalAmount(requestDto.getAmount())
                .build();
        model.addAttribute("isAfterOrder", false);
        model.addAttribute("response", responseDto);

        log.info("{} / {}", code, message);
        log.info(
                "{} / {} / {}",
                responseDto.getOrderId(),
                responseDto.getOrderName(),
                responseDto.getTotalAmount()
        );
        return "main/order/order-fail";
    }
}

