package shop.yesaladin.front.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문한 상품의 정보를 반환하기 위한 dto 클래스입니다.
 *
 * @author 배수한
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderSheetResponseDto {

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

}
