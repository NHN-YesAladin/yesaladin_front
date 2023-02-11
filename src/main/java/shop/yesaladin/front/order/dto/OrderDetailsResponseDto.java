package shop.yesaladin.front.order.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.yesaladin.front.payment.dto.PaymentResponseDto;
import shop.yesaladin.front.product.dto.ProductOrderSheetResponseDto;


/**
 * 주문 상세 조회시 사용하는 dto
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponseDto {

    //주문 기본 정보
    @NotNull
    private OrderResponseDto order;

    //상품 정보
    @NotNull
    private List<OrderProductResponseDto> orderProducts;

    //가격 정보
    private long productsAmount;
    private long discountsAmount;

    //결제 정보
    @NotNull
    private PaymentResponseDto payment;


}
