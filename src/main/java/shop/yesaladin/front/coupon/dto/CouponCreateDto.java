package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * 쿠폰 생성 요청을 위한 DTO 클래스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@ToString
@Getter
@AllArgsConstructor
public abstract class CouponCreateDto {

    private String triggerTypeCode;
    private String name;
    private Boolean isUnlimited;
    private Integer quantity;
    private Resource imageFile;
    private Integer duration;
    private LocalDate expirationDate;
    private String couponTypeCode;
    private Integer couponOpenDate;
    private String couponOpenTime;

    public MultiValueMap<String, Object> toMap() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("triggerTypeCode", triggerTypeCode);
        map.add("name", name);
        map.add("isUnlimited", isUnlimited);
        map.add("quantity", quantity);
        map.add("imageFile", imageFile);
        map.add("duration", duration);
        map.add("expirationDate", Objects.nonNull(expirationDate) ? expirationDate.toString() : null);
        map.add("expirationDate", expirationDate == null ? null : expirationDate.toString());
        map.add("couponTypeCode", couponTypeCode);
        map.add("couponOpenDate", couponOpenDate);
        map.add("couponOpenTime", couponOpenTime);

        return map;
    }
}
