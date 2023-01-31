package shop.yesaladin.front.coupon.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


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
    private Resource imageFile;
    private Integer duration;
    private LocalDate expirationDate;
    private String couponTypeCode;

    public MultiValueMap<String, Object> toMap() {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("triggerTypeCode", triggerTypeCode);
        map.add("name", name);
        map.add("isUnlimited", isUnlimited);
        map.add("quantity", quantity);
        map.add("imageFile", imageFile);
        map.add("duration", duration);
        map.add("expirationDate", expirationDate.toString());
        map.add("couponTypeCode", couponTypeCode);
        return map;
    }
}
