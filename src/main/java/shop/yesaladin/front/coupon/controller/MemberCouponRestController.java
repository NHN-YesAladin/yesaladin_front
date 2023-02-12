package shop.yesaladin.front.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.coupon.dto.CouponGiveRequestDto;
import shop.yesaladin.front.coupon.service.inter.CommandMemberCouponService;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-coupons")
public class MemberCouponRestController {

    private final CommandMemberCouponService commandMemberCouponService;

    @PostMapping
    public ResponseDto<Void> sendRequestMessage(@RequestBody CouponGiveRequestDto dto) {
        commandMemberCouponService.sendGiveRequest(dto);
        return ResponseDto.<Void>builder().success(true).status(HttpStatus.OK).build();
    }
}
