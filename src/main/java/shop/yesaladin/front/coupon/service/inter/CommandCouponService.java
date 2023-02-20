package shop.yesaladin.front.coupon.service.inter;

import shop.yesaladin.coupon.code.TriggerTypeCode;
import shop.yesaladin.front.coupon.dto.CouponCreateRequestDto;
import shop.yesaladin.front.coupon.dto.CouponCreateResponseDto;

/**
 * 쿠폰 생성에 관련된 기능을 처리하는 서비스 인터페이스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
public interface CommandCouponService {

    /**
     * 쿠폰을 생성합니다.
     *
     * @param createDto 쿠폰 생성 정보를 담고 있는 DTO 클래스
     * @return 생성된 쿠폰의 이름
     */
    CouponCreateResponseDto createCouponTemplate(CouponCreateRequestDto createDto);

    void stopIssueCoupon(TriggerTypeCode triggerTypeCode, long couponId);
}
