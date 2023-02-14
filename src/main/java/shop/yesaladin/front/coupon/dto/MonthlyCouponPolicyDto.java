package shop.yesaladin.front.coupon.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyCouponPolicyDto {

    Long couponId;
    LocalDateTime openDateTime;
}
