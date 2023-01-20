package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.Getter;

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
