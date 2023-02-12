package shop.yesaladin.front.coupon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseDto<Void> sendRequestMessage(@RequestBody CouponGiveRequestDto dto, HttpServletResponse httpServletResponse)
            throws IOException {
        ResponseDto<Void> responseDto = commandMemberCouponService.sendGiveRequest(dto).getBody();

        if (!responseDto.isSuccess() && responseDto.getErrorMessages()
                .get(0)
                .contains("already has")) {
            // 중복 요청 alert
            httpServletResponse.setContentType("text/html; charset=euc-kr");
            PrintWriter out = httpServletResponse.getWriter();
            out.println("<script>alert('이미 발급된 쿠폰입니다.'); </script>");
            out.flush();
        }

        return ResponseDto.<Void>builder().success(true).status(HttpStatus.OK).build();
    }
}
