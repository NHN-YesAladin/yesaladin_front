package shop.yesaladin.front.coupon.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.yesaladin.coupon.trigger.CouponBoundCode;
import shop.yesaladin.coupon.trigger.CouponTypeCode;
import shop.yesaladin.coupon.trigger.TriggerTypeCode;
import shop.yesaladin.front.config.FrontServerMetaConfig;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponCreateRequestDto;
import shop.yesaladin.front.coupon.dto.CouponCreateResponseDto;
import shop.yesaladin.front.coupon.service.inter.CommandCouponService;

/**
 * 쿠폰 관련 관리자 페이지 컨트롤러입니다.
 *
 * @author 김홍대
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/manager/coupon")
public class CouponManagerWebController {

    private final CommandCouponService commandCouponService;
    private final FrontServerMetaConfig frontServerMetaConfig;
    private final GatewayConfig gatewayConfig;

    @GetMapping("/create")
    public String couponTemplateCreateView(
            Model model, @RequestParam(required = false) String createdCouponName
    ) {
        model.addAttribute("requestDto", new CouponCreateRequestDto());
        model.addAttribute("couponTypeCodes", CouponTypeCode.values());
        model.addAttribute("triggerTypeCodes", TriggerTypeCode.values());
        model.addAttribute("couponBoundCodes", CouponBoundCode.values());
        model.addAttribute("createdCouponName", createdCouponName);
        model.addAttribute("frontServerUrl", frontServerMetaConfig.getFrontServerUrl());
        model.addAttribute("shopServerUrl", gatewayConfig.getShopUrl());
        return "manager/coupon/manager-coupon-create-view";
    }

    @PostMapping("/create")
    @ResponseBody
    public CouponCreateResponseDto createCouponTemplate(
            @ModelAttribute @Valid CouponCreateRequestDto requestDto
    ) {
        return commandCouponService.createCouponTemplate(requestDto);
    }

    @GetMapping
    public String couponTemplateListView() {
        return "manager/coupon/manager-coupon-list-view";
    }
}
