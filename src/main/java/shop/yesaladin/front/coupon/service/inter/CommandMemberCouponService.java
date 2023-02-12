package shop.yesaladin.front.coupon.service.inter;

import org.springframework.http.ResponseEntity;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.coupon.dto.CouponGiveRequestDto;

/**
 * 멤버가 보유한 쿠폰 관련 커맨드를 수행하는 서비스 인터페이스입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
public interface CommandMemberCouponService {

    /**
     * 쿠폰 지급 요청 메시지를 보냅니다.
     *
     * @param dto 쿠폰 지급 요청 정보
     * @return
     */
    ResponseEntity<ResponseDto<Void>> sendGiveRequest(CouponGiveRequestDto dto);
}
