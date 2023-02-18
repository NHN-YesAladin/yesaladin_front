package shop.yesaladin.front.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private final float PERCENT_DENOMINATOR_VALUE = 100;
    private final long ROUND_OFF_VALUE = 10;

    private Long productId;
    private String isbn;
    private String title;
    private Long actualPrice;
    private int discountRate;
    private Boolean isGivenPoint;
    private int givenPointRate;
    private int quantity;
    private List<String> categories;

    /**
     * 상품의 할인 가격을 반환합니다.
     *
     * @return 할인 가격
     * @author 최예린
     * @since 1.0
     */
    public Long getSalePrice() {
        if (discountRate == 0) {
            return actualPrice;
        }
        return Math.round((actualPrice - actualPrice * discountRate / PERCENT_DENOMINATOR_VALUE)
                / ROUND_OFF_VALUE) * ROUND_OFF_VALUE;
    }

    /**
     * 상품의 적립예상 포인트를 반환합니다.
     *
     * @return 적립 예상 포인트
     * @author 최예린
     * @since 1.0
     */
    public Long getExpectedPoint() {
        return isGivenPoint && givenPointRate != 0 ? Math.round(
                (actualPrice * givenPointRate / PERCENT_DENOMINATOR_VALUE) / ROUND_OFF_VALUE)
                * ROUND_OFF_VALUE : 0;
    }
}
