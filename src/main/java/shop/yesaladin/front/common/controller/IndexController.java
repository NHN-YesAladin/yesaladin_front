package shop.yesaladin.front.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;
import shop.yesaladin.front.member.dto.MemberGrade;
import shop.yesaladin.front.member.service.inter.QueryMemberService;
import shop.yesaladin.front.order.service.inter.QueryOrderService;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;
import shop.yesaladin.front.product.dto.ProductRecentResponseDto;
import shop.yesaladin.front.product.dto.RecentViewProductRequestDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.wishlist.service.inter.QueryWishlistService;

/**
 * 메인 페이지, 마이 페이지, 관리자 페이지를 리턴하기 위한 Controller 클래스 입니다.
 *
 * @author 송학현
 * @author 최예린
 * @author 김선홍
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final QueryMemberService queryMemberService;
    private final QueryCouponService queryCouponService;
    private final QueryOrderService queryOrderService;
    private final QueryPointHistoryService pointHistoryService;
    private final QueryProductService queryProductService;
    private final QueryWishlistService queryWishlistService;
    private final CookieUtils cookieUtils;
    private final ObjectMapper objectMapper;
    private static final String COOKIE = "recent";

    /**
     * 메인페이지를 반환시켜줍니다. 베스트셀러 및 신간 상품과 최근 본 상품 리스트를 가져옵니다.
     *
     * @return 메인페이지
     * @author 송학현
     * @author 김선홍
     * @author 이수정
     * @since 1.0
     */
    @GetMapping
    public String main(
            Model model,
            @CookieValue(required = false, name = COOKIE) Cookie recentViewProductList
    ) throws JsonProcessingException {
        model.addAttribute(
                "bestseller",
                queryOrderService.getBestSeller()
        );

        model.addAttribute(
                "recentProductList",
                queryProductService.findRecentProduct(PageRequest.of(0, 12))
        );
        List<Long> recentViewList = new ArrayList<>(getPageRecentViewProductList(cookie, response));
        model.addAttribute(
                "recentViewProductList",
                sort(
                        recentViewList,
                        queryProductService.findRecentViewProduct(
                                new RecentViewProductRequestDto(
                                        new ArrayList<>(getTotalRecentViewProductList(cookie, response)),
                                        recentViewList
                                ),
                                PageRequest.of(0, 10)
                        ).getDataList()
                )
        );
        return "main/index";
    }

    /**
     * 마이페이지를 반환시켜줍니다.
     *
     * @return 마이페이지
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/mypage")
    public String mypage(
            Model model,
            Authentication authentication,
            @CookieValue(name = "recent", required = false) Cookie cookie,
            HttpServletResponse response
    ) throws JsonProcessingException {
        long point = pointHistoryService.getMemberPoint();
        MemberGrade grade = MemberGrade.valueOf(queryMemberService.getMemberGrade());
        PaginatedResponseDto<MemberCouponSummaryDto> memberCouponList = queryCouponService.getMemberCouponList(
                authentication.getName(),
                true,
                PageRequest.of(0, 1)
        );

        List<Long> recentViewList = new ArrayList<>(getPageRecentViewProductList(cookie, response));
        model.addAttribute("recentViewList", sort(
                recentViewList,
                queryProductService.findRecentViewProduct(
                                new RecentViewProductRequestDto(
                                        new ArrayList<>(getTotalRecentViewProductList(cookie, response)),
                                        recentViewList
                                ),
                                PageRequest.of(0, 12)
                        )
                        .getDataList()
        ));
        model.addAttribute(
                "wishlist",
                queryWishlistService.getWishlist(PageRequest.of(0, 12)).getDataList()
        );
        model.addAttribute("point", point);
        model.addAttribute("grade", grade);
        model.addAttribute("coupon", memberCouponList.getTotalDataCount());

        return "mypage/index";
    }

    private Set<Long> getTotalRecentViewProductList(Cookie cookie, HttpServletResponse response)
            throws JsonProcessingException {
        if (Objects.nonNull(cookie)) {
            return objectMapper.readValue(
                    URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8),
                    new TypeReference<LinkedHashSet<Long>>() {
                    }
            );
        }
        Set<Long> recentViewList = new LinkedHashSet<>();
        response.addCookie(createCookie(recentViewList));
        return recentViewList;
    }

    private Set<Long> getPageRecentViewProductList(Cookie cookie, HttpServletResponse response)
            throws JsonProcessingException {
        if (Objects.nonNull(cookie)) {
            Set<Long> set = objectMapper.readValue(
                    URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8),
                    new TypeReference<LinkedHashSet<Long>>() {
                    }
            );
            List<Long> list = new ArrayList<>(set);
            Collections.reverse(list);
            int size = Math.min(list.size(), 10);
            return new LinkedHashSet<>(list.subList(0, size));
        }
        Set<Long> recentViewList = new LinkedHashSet<>();
        response.addCookie(createCookie(recentViewList));
        return recentViewList;
    }

    private List<ProductRecentResponseDto> sort(
            List<Long> pageIds,
            List<ProductRecentResponseDto> recentViewlist
    ) {
        List<ProductRecentResponseDto> list = new ArrayList<>();
        for (Long id : pageIds) {
            for (ProductRecentResponseDto productRecentResponseDto : recentViewlist) {
                if (productRecentResponseDto.getId().equals(id)) {
                    list.add(productRecentResponseDto);
                    break;
                }
            }
        }
        return list;
    }

    private Cookie createCookie(Set<Long> value) throws JsonProcessingException {
        return cookieUtils.createCookie(
                COOKIE,
                URLEncoder.encode(objectMapper.writeValueAsString(value), StandardCharsets.UTF_8),
                259200
        );
    }
}
