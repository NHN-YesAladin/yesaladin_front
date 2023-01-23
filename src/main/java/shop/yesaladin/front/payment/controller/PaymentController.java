package shop.yesaladin.front.payment.controller;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Base64;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.dto.CategorySaveRequestDto;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.payment.dto.PaymentCompleteSimpleResponseDto;
import shop.yesaladin.front.payment.dto.PaymentRequestDto;
import shop.yesaladin.front.payment.service.inter.PaymentService;

/**
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/success")
    public String success(
            @RequestParam("paymentKey") String paymentKey,
            @RequestParam("orderId") String orderId,
            @RequestParam("amount") Long amount
            ) throws IOException {

        PaymentRequestDto requestDto = new PaymentRequestDto(
                paymentKey,
                orderId,
                amount
        );
        //TODO 주문이 이미 생성이 되어 있어야 shop서버와 통신하여 결제 완료처리 가능
        PaymentCompleteSimpleResponseDto responseDto = paymentService.confirm(requestDto);

        return "/order/order-complete";
    }

    @RequestMapping("/fail")
    public String failPayment(@RequestParam String message, @RequestParam String code, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("code", code);

        log.error("{} / {}", code, message);
        return "/order/fail";
    }

}

