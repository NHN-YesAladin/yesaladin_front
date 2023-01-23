package shop.yesaladin.front.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 토스 통신시 success url로 들어오는 요청을 shop서버로 전달하기 위한 dto
 *
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@AllArgsConstructor
public class PaymentRequestDto {
    private String paymentKey;
    private String orderId;
    private Long amount;

}
