package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 결제 생성을 위한 주문 관련 dto
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@AllArgsConstructor
public class OrderPaymentRequestDto {

    private String orderNumber;
    private String orderName;
    private Long totalAmount;

}
