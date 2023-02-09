package shop.yesaladin.front.coupon.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.coupon.code.TriggerTypeCode;

@Controller
@RequestMapping("/coupon")
public class CouponEventWebController {

    @GetMapping
    public String couponMainPageView() {
        return "/main/coupon/coupon-main-page";
    }

    @GetMapping("/coupon-of-the-month")
    public String couponOfTheMonthPageView(Model model) {
        model.addAttribute("type", TriggerTypeCode.COUPON_OF_THE_MONTH);
        // TODO 이달의 쿠폰 - 쿠폰 id 가져오기
        model.addAttribute("couponID", 693);
        return "/main/coupon/coupon-of-the-month-page";
    }
}
