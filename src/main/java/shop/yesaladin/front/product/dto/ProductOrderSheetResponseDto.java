package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 주문한 상품의 정보를 반환하기 위한 dto 클래스입니다.
 * <p>
 * ProductOrderResponseDto 와 동일한 클래스지만 이름이 달라 deserializing 불가
 *
 * @author 배수한
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderSheetResponseDto {
    private final float PERCENT_DENOMINATOR_VALUE = 100;
    private final long ROUND_OFF_VALUE = 10;

    @NotNull
    private Long productId;
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    private long actualPrice;
    private int discountRate;
    @NotNull
    private Boolean isGivenPoint;
    private int givenPointRate;
    private long quantity;

    /**
     * 상품의 할인 가격을 반환합니다.
     *
     * @return 할인 가격
     * @author 배수한
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
     * @author 배수한
     * @since 1.0
     */
    public Long getExpectedPoint() {
        return isGivenPoint && givenPointRate != 0 ? Math.round(
                (actualPrice * givenPointRate / PERCENT_DENOMINATOR_VALUE) / ROUND_OFF_VALUE)
                * ROUND_OFF_VALUE : 0;
    }

}
