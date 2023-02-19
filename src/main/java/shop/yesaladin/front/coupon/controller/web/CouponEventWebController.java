package shop.yesaladin.front.coupon.controller.web;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

/**
 * 쿠폰 다운로드 페이지 관련 요청을 처리하는 controller 입니다.
 *
 * @author 김홍대
 * @author 서민지
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/coupon")
public class CouponEventWebController {

    private final GatewayConfig gatewayConfig;
    private final QueryMemberService queryMemberService;
    private final FrontServerMetaConfig frontServerMetaConfig;
    private final QueryCouponService queryCouponService;

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
        model.addAttribute("socketServerUrl", gatewayConfig.getSocketUrl());
        return "main/coupon/coupon-main-page";
    }

    /**
     * 이달의 쿠폰 페이지 화면을 출력합니다. 전달되는 이달의 쿠폰은 가장 최근에 등록된 쿠폰입니다.
     *
     * @param model
     * @return 이달의 쿠폰 뷰 페이지
     */
    @GetMapping("/coupon-of-the-month")
    public String couponOfTheMonthPageView(Model model) {
        model.addAttribute("type", TriggerTypeCode.COUPON_OF_THE_MONTH);
        model.addAttribute("frontServerUrl", frontServerMetaConfig.getFrontServerUrl());
        model.addAttribute("shopServerUrl", gatewayConfig.getShopUrl());
        model.addAttribute("couponPolicy", queryCouponService.getMonthlyCouponPolicy());
        model.addAttribute("socketServerUrl", gatewayConfig.getSocketUrl());
        return "main/coupon/monthly-coupon-page";
    }
}
