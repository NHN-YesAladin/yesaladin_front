package shop.yesaladin.front.coupon.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.coupon.dto.CouponCreateRequestDto;

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
    public String couponTemplateCreateView(Model model) {
        model.addAttribute("requestDto", new CouponCreateRequestDto());
        return "coupon/manager-coupon-create-view";
    }

    @PostMapping("/create")
    public String createCouponTemplate(@ModelAttribute @Valid CouponCreateRequestDto requestDto) {
        return "redirect:/manager/coupon/create";
    }
}
