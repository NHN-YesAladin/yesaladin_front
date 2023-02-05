package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문한 상품의 정보를 반환하기 위한 dto 클래스입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderResponseDto {

    private Long productId;
    private String isbn;
    private String title;
    private Long actualPrice;
    private int discountRate;
    private int expectedPoint;
    private int quantity;
}
