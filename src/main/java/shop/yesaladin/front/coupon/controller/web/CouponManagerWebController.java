package shop.yesaladin.front.coupon.controller.web;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.yesaladin.coupon.code.CouponBoundCode;
import shop.yesaladin.coupon.code.CouponTypeCode;
import shop.yesaladin.coupon.code.TriggerTypeCode;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.config.FrontServerMetaConfig;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponCreateRequestDto;
import shop.yesaladin.front.coupon.dto.CouponCreateResponseDto;
import shop.yesaladin.front.coupon.dto.CouponSummaryDto;
import shop.yesaladin.front.coupon.service.inter.CommandCouponService;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;

/**
 * 쿠폰 관련 관리자 페이지 컨트롤러입니다.
 *
 * @author 김홍대, 서민지
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/manager/coupon")
public class CouponManagerWebController {

    private final CommandCouponService commandCouponService;
    private final QueryCouponService queryCouponService;
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

    /**
     * 모든 트리거에 대한 쿠폰을 조회합니다.
     *
     * @param pageable 조회할 쿠폰 목록의 페이지 번호와 크기
     * @param model
     * @return 관리자페이지 내 쿠폰 목록 조회 뷰
     */
    @GetMapping
    public String couponTemplateListView(
            @PageableDefault Pageable pageable, Model model
    ) {
        PaginatedResponseDto<CouponSummaryDto> response = queryCouponService.getTriggeredCouponList(
                pageable);

        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());

        return "manager/coupon/manager-coupon-list-view";
    }


}
