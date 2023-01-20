package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CouponCreateDto {

    private String triggerTypeCode;
    private String name;
    private Boolean isUnlimited;
    private Integer quantity;
    private String fileUri;
    private Integer duration;
    private LocalDate expirationDate;
    private String couponTypeCode;
}
