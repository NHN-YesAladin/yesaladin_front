package shop.yesaladin.front.coupon.service.inter;

import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.coupon.dto.CouponSummaryDto;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;

/**
 * 쿠폰 조회 관련 기능을 처리하는 서비스 인터페이스입니다.
 *
 * @author 서민지
 * @since 1.0
 */
public interface QueryCouponService {

    /**
     * 모든 트리거에 대한 쿠폰을 페이지네이션하여 조회합니다.
     *
     * @param pageable 페이지 사이즈와 페이지 번호
     * @return 페이지네이션 된 트리거를 가진 쿠폰 요약 정보
     */
    PaginatedResponseDto<CouponSummaryDto> getTriggeredCouponList(Pageable pageable);

    PaginatedResponseDto<MemberCouponSummaryDto> getMemberCouponList(
            String loginId,
            boolean canUse,
            Pageable pageable
    );
}
