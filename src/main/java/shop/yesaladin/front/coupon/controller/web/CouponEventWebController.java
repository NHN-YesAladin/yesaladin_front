package shop.yesaladin.front.coupon.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.coupon.code.TriggerTypeCode;
import shop.yesaladin.front.config.GatewayConfig;
import shop.yesaladin.front.coupon.dto.CouponIssueResponseDto;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/coupon")
public class CouponEventWebController {

    private final GatewayConfig gatewayConfig;
    private final RestTemplate restTemplate;
    private int couponID = 14;

    @GetMapping
    public String couponMainPageView() {
        return "/main/coupon/coupon-main-page";
    }

    @GetMapping("/coupon-of-the-month")
    public String couponOfTheMonthPageView(Model model) {
        model.addAttribute("type", TriggerTypeCode.COUPON_OF_THE_MONTH);
        // TODO 이달의 쿠폰 - 쿠폰 id 가져오기
        model.addAttribute("couponID", this.couponID);
        return "/main/coupon/coupon-of-the-month-page";
    }

    @GetMapping(value = "/issuance", params = {"type", "coupon-id"})
    public String requestCouponIssue(
            @RequestParam("type") String triggerType,
            @RequestParam("coupon-id") Long couponId,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        log.info("********* request coupon issue to shop server ********");
        // request issue coupon with triggerType and couponid to shop server
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(gatewayConfig.getShopUrl())
                .pathSegment("v1", "member-coupons", "issuance")
                .queryParam("type", triggerType)
                .queryParam("coupon-id", couponId)
                .build();

        log.info("=== coupon issue request url {} ===", uriComponents);

        ResponseEntity<ResponseDto<CouponIssueResponseDto>> response = restTemplate.exchange(uriComponents.toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        ResponseDto<CouponIssueResponseDto> responseBody = response.getBody();

        assert responseBody != null;
        if (!responseBody.isSuccess() && responseBody.getErrorMessages()
                .get(0)
                .contains("already has")) {
            // 중복 요청 alert
            httpServletResponse.setContentType("text/html; charset=euc-kr");
            PrintWriter out = httpServletResponse.getWriter();
            out.println("<script>alert('이미 발급된 쿠폰입니다.'); </script>");
            out.flush();
            return "redirect:/coupon/coupon-of-the-month";
        }

        return "/main/coupon/coupon-of-the-month-page";
    }
}
