package shop.yesaladin.front.payment.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.payment.dto.PaymentCompleteSimpleResponseDto;
import shop.yesaladin.front.payment.dto.PaymentRequestDto;
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
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 토스 페이먼츠의 결제 중 결제 승인 시퀀스를 처리하기 위해 successUrl로 지정된 컨트롤러 메서드
     *
     * @param paymentKey 결제 구분 키
     * @param orderId    주문 번호
     * @param amount     결제 총 금액
     * @return 주문 완료 화면
     */
    @GetMapping("/success")
    public String success(
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") Long amount,
            Model model
    ) {
        PaymentRequestDto requestDto = new PaymentRequestDto(
                paymentKey,
                orderId,
                amount
        );
        //TODO 주문이 이미 생성이 되어 있어야 shop서버와 통신하여 결제 완료처리 가능
        PaymentCompleteSimpleResponseDto responseDto = paymentService.confirm(requestDto);
        System.out.println("responseDto = " + responseDto);
        model.addAttribute("response", responseDto);

        return "main/order/order-complete";
    }

    /**
     * 토스 페이먼츠를 통한 결제 실패시, 돌아오는 컨트롤러 메서드
     *
     * @param message 결제 실패 내용
     * @param code HTTP status code
     * @param model
     * @return
     */
    @RequestMapping("/fail")
    public String failPayment(
            @RequestParam String message,
            @RequestParam String code,
            Model model
    ) {
        //TODO 결제 실패시 정책 수립 후 화면 꾸미기
        model.addAttribute("message", message);
        model.addAttribute("code", code);

        log.error("{} / {}", code, message);
        return "main/order/fail";
    }

}

