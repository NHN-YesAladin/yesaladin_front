package shop.yesaladin.front.coupon.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 상품에 쿠폰을 적용하기위한 정보를 담은 dto입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponOrderSheetRequestDto {
    private String isbn;
    private String quantity;
    private String couponCode;
    private List<String> duplicateCouponCode;
}
