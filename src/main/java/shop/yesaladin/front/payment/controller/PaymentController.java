package shop.yesaladin.front.payment.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
//     * @param paymentKey 결제 구분 키
//     * @param orderId    주문 번호
//     * @param amount     결제 총 금액
     * @return 주문 완료 화면
     */
    @GetMapping("/success")
    public String success(
            @ModelAttribute PaymentRequestDto requestDto,
            Model model
    ) {
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
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String code,
            @RequestParam(name = "o-num") String orderNumber,
            @RequestParam(name = "o-name") String orderName,
            @RequestParam(name = "o-amount") String amount,
            Model model
    ) {
        //TODO 결제 실패시 정책 수립 후 화면 꾸미기
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("orderName", orderName);
        model.addAttribute("amount", amount);

        log.info("{} / {}", code, message);
        log.info("{} / {} / {}", orderNumber, orderName, amount);
        return "main/order/order-fail";
    }

}

