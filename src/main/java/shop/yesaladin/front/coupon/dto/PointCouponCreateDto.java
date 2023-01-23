package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.Getter;


/**
 * 포인트 충전 쿠폰 생성 요청을 위한 DTO 클래스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Getter
public class PointCouponCreateDto extends CouponCreateDto {

    private Integer chargePointAmount;

    public PointCouponCreateDto(
            String triggerTypeCode,
            String name,
            Boolean isUnlimited,
            Integer quantity,
            String fileUri,
            Integer duration,
            LocalDate expirationDate,
            String couponTypeCode,
            Integer chargePointAmount
    ) {
        super(
                triggerTypeCode,
                name,
                isUnlimited,
                quantity,
                fileUri,
                duration,
                expirationDate,
                couponTypeCode
        );
        this.chargePointAmount = chargePointAmount;
    }
}
