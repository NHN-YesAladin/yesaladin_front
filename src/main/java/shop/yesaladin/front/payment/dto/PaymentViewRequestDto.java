package shop.yesaladin.front.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 주문 화면에서 결제 화면으로 넘어올때 사용하는 요청 dto
 *
 * @author 배수한
 * @since 1.0
 */

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentViewRequestDto {

    private String ordererName;
    private String ordererPhoneNumber;
    private String recipientName;
    private String recipientPhoneNumber;

    private String recipientAddress;
    private String recipientExpectedDate; //String 맞음

    private String orderNumber;
    private String orderName;
    private Long productAmount;
    private Long discountAmount;
    private Long shippingFee;
    private Long wrappingFee;
    private Long totalAmount;

}
