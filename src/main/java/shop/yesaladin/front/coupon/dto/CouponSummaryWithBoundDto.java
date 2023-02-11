package shop.yesaladin.front.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.yesaladin.coupon.code.CouponBoundCode;

@Getter
@AllArgsConstructor
public class CouponSummaryWithBoundDto {

    private final CouponSummaryDto couponSummaryDto;
    private final CouponBoundCode boundCode;
    private final String displayBound;

}
