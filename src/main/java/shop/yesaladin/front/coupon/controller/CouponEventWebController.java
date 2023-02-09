package shop.yesaladin.front.coupon.controller;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coupon")
public class CouponEventWebController {

    @GetMapping
    public String getCouponMainPage(Model model) {
        model.addAttribute("today", LocalDate.now());
        return "/main/coupon/coupon-main-page";
    }

    @GetMapping("/coupon-of-the-month")
    public String getCouponOfTheMonthPage() {
        return "/main/coupon/coupon-of-the-month-page";
    }
}
