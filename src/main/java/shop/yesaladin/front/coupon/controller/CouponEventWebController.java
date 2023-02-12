package shop.yesaladin.front.coupon.controller;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.coupon.code.TriggerTypeCode;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.FrontServerMetaConfig;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponSummaryWithBoundDto;
import shop.yesaladin.front.coupon.service.inter.CommandMemberCouponService;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/coupon")
public class CouponEventWebController {

    private final QueryMemberService queryMemberService;
    private final QueryCouponService queryCouponService;
    private final FrontServerMetaConfig frontServerMetaConfig;
    private final GatewayConfig gatewayConfig;

    @GetMapping
    public String getCouponMainPage(Model model, Pageable pageable) {
        String memberGrade = queryMemberService.getMemberGrade();
        String gradeCode = "MEMBER_GRADE_" + memberGrade.split("\\(")[0];
        PaginatedResponseDto<CouponSummaryWithBoundDto> couponList = queryCouponService.getCouponByTriggerTypeCode(
                TriggerTypeCode.valueOf(gradeCode),
                pageable
        );
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("memberGrade", memberGrade);
        model.addAttribute("gradeCode", gradeCode);
        model.addAttribute("couponList", couponList);
        model.addAttribute("frontServerUrl", frontServerMetaConfig.getFrontServerUrl());
        model.addAttribute("shopServerUrl", gatewayConfig.getShopUrl());
        return "/main/coupon/coupon-main-page";
    }

    @GetMapping("/coupon-of-the-month")
    public String getCouponOfTheMonthPage() {
        return "/main/coupon/coupon-of-the-month-page";
    }
}
