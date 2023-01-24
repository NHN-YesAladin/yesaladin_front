package shop.yesaladin.front.coupon.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 쿠폰 생성 요청에 대한 응답을 받기 위한 DTO 클래스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class CouponCreateResponseDto {

    private String name;
    private String couponTypeCode;

    private List<String> errorMessageList;
}
