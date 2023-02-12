package shop.yesaladin.front.coupon.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.exception.Custom4xxException;
import shop.yesaladin.front.coupon.dto.CouponGiveRequestDto;
import shop.yesaladin.front.coupon.dto.RequestIdOnlyDto;
import shop.yesaladin.front.coupon.service.inter.CommandMemberCouponService;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-coupons")
public class MemberCouponRestController {

    private final CommandMemberCouponService commandMemberCouponService;

    @PostMapping
    public ResponseDto<RequestIdOnlyDto> sendRequestMessage(@RequestBody CouponGiveRequestDto dto) {
        RequestIdOnlyDto response = commandMemberCouponService.sendGiveRequest(dto);
        return ResponseDto.<RequestIdOnlyDto>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Custom4xxException.class)
    public ResponseDto<RequestIdOnlyDto> handleException(Custom4xxException e) {
        return ResponseDto.<RequestIdOnlyDto>builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .data(null)
                .errorMessages(List.of(e.getMessage()))
                .build();
    }
}
