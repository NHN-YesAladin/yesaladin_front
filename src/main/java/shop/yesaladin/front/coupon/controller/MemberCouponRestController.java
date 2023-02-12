package shop.yesaladin.front.coupon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.coupon.dto.CouponGiveRequestDto;
import shop.yesaladin.front.coupon.service.inter.CommandMemberCouponService;

/**
 * 회원 쿠폰 관련 요청을 처리하는 controller 입니다.
 *
 * @author 김홍대
 * @author 서민지
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("member-coupons")
public class MemberCouponRestController {

    private final CommandMemberCouponService commandMemberCouponService;

    /**
     * 쿠폰의 트리거 타입과 쿠폰 아이디를 통해 로그인한 회원에게 쿠폰 발행을 요청합니다.
     *
     * @param dto 쿠폰의 트리거 타입과 아이디를 가진 dto
     * @return 회원의 쿠폰 발행 요청에 대한 응답
     */
    @PostMapping
    public ResponseDto<Void> sendRequestMessage(@RequestBody CouponGiveRequestDto dto) {
        ResponseDto<Void> responseDto = commandMemberCouponService.sendGiveRequest(dto).getBody();

        if (!responseDto.isSuccess() && responseDto.getErrorMessages()
                .get(0)
                .contains("already has")) {
            // 중복 요청 alert
            log.info("[COUPON] Member already has coupon.");
        }

        return ResponseDto.<Void>builder().success(true).status(HttpStatus.OK).build();
    }
}
