package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.yesaladin.coupon.code.CouponBoundCode;
import shop.yesaladin.coupon.code.CouponTypeCode;

/**
 * 회원 쿠폰 목록에 필요한 쿠폰 요약 정보를 담은 dto 입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCouponSummaryDto {

    private String name;
    private String couponCode;
    private int amount;
    private CouponTypeCode couponTypeCode;
    private LocalDate expireDate;
    private Boolean isUsed;
    private String couponBound; // isbn/categoryId/null
    private CouponBoundCode couponBoundCode;

    public static MemberCouponSummaryDto replaceBoundToDisplayBound(
            MemberCouponSummaryDto dto,
            String displayBound
    ) {
        return MemberCouponSummaryDto.builder()
                .name(dto.getName())
                .couponCode(dto.getCouponCode())
                .amount(dto.getAmount())
                .couponTypeCode(dto.getCouponTypeCode())
                .expireDate(dto.getExpireDate())
                .isUsed(dto.getIsUsed())
                .couponBound(displayBound)
                .couponBoundCode(dto.getCouponBoundCode())
                .build();
    }
}
