package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.Getter;

/**
 * 정액 할인 쿠폰 생성 요청을 위한 DTO 클래스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Getter
public class AmountCouponCreateDto extends CouponCreateDto {

    private int minOrderAmount;
    private int discountAmount;
    private boolean canBeOverlapped;
    private String couponBoundCode;
    private String isbn;
    private String categoryId;

    public AmountCouponCreateDto(
            String triggerTypeCode,
            String name,
            Boolean isUnlimited,
            Integer quantity,
            String fileUri,
            Integer duration,
            LocalDate expirationDate,
            String couponTypeCode,
            int minOrderAmount,
            int discountAmount,
            boolean canBeOverlapped,
            String couponBoundCode,
            String isbn,
            String categoryId
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
        this.minOrderAmount = minOrderAmount;
        this.discountAmount = discountAmount;
        this.canBeOverlapped = canBeOverlapped;
        this.couponBoundCode = couponBoundCode;
        this.isbn = isbn;
        this.categoryId = categoryId;
    }
}
