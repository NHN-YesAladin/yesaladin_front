package shop.yesaladin.front.order.dto;

import lombok.*;
import shop.yesaladin.front.product.dto.ProductOrderSheetResponseDto;

/**
 * 주문 상품을 조회 하기 위한 dto
 *
 * @author 배수한
 * @since 1.0
 */

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDto {

    private ProductOrderSheetResponseDto productDto;
    private int quantity;

}
