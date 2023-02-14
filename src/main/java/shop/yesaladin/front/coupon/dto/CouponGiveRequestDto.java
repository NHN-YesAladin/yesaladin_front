package shop.yesaladin.front.coupon.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import shop.yesaladin.coupon.code.TriggerTypeCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CouponGiveRequestDto {

    private TriggerTypeCode triggerTypeCode;
    private Long couponId;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime requestDateTime;
}
