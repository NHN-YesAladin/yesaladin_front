package shop.yesaladin.front.coupon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 쿠폰 관련 관리자 페이지 컨트롤러입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@Controller
@RequestMapping("/manager/coupon")
public class CouponManagerWebController {

    @GetMapping("/create")
    public String couponTemplateCreateView() {
        return "coupon/manager-coupon-create-view";
    }
}
