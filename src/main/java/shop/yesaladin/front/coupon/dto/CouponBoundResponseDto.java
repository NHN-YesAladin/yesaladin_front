package shop.yesaladin.front.coupon.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.yesaladin.coupon.code.CouponBoundCode;

/**
 * 쿠폰 범위 조회 요청의 응답 dto 입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponBoundResponseDto {

    private long couponId;
    private CouponBoundCode boundCode;
    private String bound;

}