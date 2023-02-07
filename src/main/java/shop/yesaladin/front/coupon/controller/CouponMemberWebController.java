package shop.yesaladin.front.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mypage/coupon")
public class CouponMemberWebController {

    private final QueryCouponService queryCouponService;

    @GetMapping
    public String memberCouponListPage(
            Authentication authentication,
            Model model,
            @RequestParam(defaultValue = "true") boolean usable,
            Pageable pageable
    ) {
        PaginatedResponseDto<MemberCouponSummaryDto> memberCouponList = queryCouponService.getMemberCouponList(
                authentication.getName(),
                usable,
                pageable
        );
        model.addAttribute("memberCouponList", memberCouponList);
        return "mypage/coupon/coupon-list";
    }
}
