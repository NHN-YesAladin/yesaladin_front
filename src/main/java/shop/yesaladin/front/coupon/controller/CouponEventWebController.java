package shop.yesaladin.front.coupon.controller;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.member.service.inter.QueryMemberService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/coupon")
public class CouponEventWebController {

    private final QueryMemberService queryMemberService;

    @GetMapping
    public String getCouponMainPage(Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("memberGrade", queryMemberService.getMemberGrade());
        return "/main/coupon/coupon-main-page";
    }

    @GetMapping("/coupon-of-the-month")
    public String getCouponOfTheMonthPage() {
        return "/main/coupon/coupon-of-the-month-page";
    }
}
