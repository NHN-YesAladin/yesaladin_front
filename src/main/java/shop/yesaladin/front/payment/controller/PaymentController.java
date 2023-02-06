package shop.yesaladin.front.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.common.dto.ResponseDto;
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
            return "main/order/order-fail";
        }

        return "main/order/order-complete";
    }


    /**
     * 임시 구현 : 토스 failUrl의 대처 방법 고안
     * 2023.02.05 배수한
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

