package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 생성시 반환하는 dto 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateResponseDto {

    private Long orderId;
    private String orderNumber;
    private String orderName;
    private Long totalAmount;
    private Integer shippingFee;
    private Integer wrappingFee;
}