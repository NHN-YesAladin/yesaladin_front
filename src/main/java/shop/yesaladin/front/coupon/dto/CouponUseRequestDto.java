package shop.yesaladin.front.coupon.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponUseRequestDto {

    private List<String> couponCodes;
}