package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 쿠폰 생성 요청을 위한 DTO 클래스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
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
