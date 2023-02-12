package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.util.MultiValueMap;

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
            Resource imageFile,
            Integer duration,
            LocalDate expirationDate,
            String couponTypeCode,
            Integer couponOpenDate,
            String couponOpenTime,
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
                imageFile,
                duration,
                expirationDate,
                couponTypeCode,
                couponOpenDate,
                couponOpenTime
        );
        this.minOrderAmount = minOrderAmount;
        this.discountAmount = discountAmount;
        this.canBeOverlapped = canBeOverlapped;
        this.couponBoundCode = couponBoundCode;
        this.isbn = isbn;
        this.categoryId = categoryId;
    }

    @Override
    public MultiValueMap<String, Object> toMap() {
        MultiValueMap<String, Object> map = super.toMap();
        map.add("minOrderAmount", minOrderAmount);
        map.add("discountAmount", discountAmount);
        map.add("canBeOverlapped", canBeOverlapped);
        map.add("couponBoundCode", couponBoundCode);
        map.add("isbn", isbn);
        map.add("categoryId", categoryId);
        return map;
    }
}
