package shop.yesaladin.front.coupon.controller;

import java.io.IOException;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("member-coupons")
public class MemberCouponRestController {

    private final CommandMemberCouponService commandMemberCouponService;

    @PostMapping
    public ResponseDto<Void> sendRequestMessage(@RequestBody CouponGiveRequestDto dto)
            throws IOException {
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
