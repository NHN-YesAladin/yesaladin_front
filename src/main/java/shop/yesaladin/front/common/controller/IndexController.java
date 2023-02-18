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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.coupon.dto.MemberCouponSummaryDto;
import shop.yesaladin.front.coupon.service.inter.QueryCouponService;
import shop.yesaladin.front.member.dto.MemberGrade;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;
import shop.yesaladin.front.product.dto.ProductRecentResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.statistics.dto.PercentageResponseDto;
import shop.yesaladin.front.wishlist.service.inter.QueryWishlistService;

/**
 * 메인 페이지, 마이 페이지, 관리자 페이지를 리턴하기 위한 Controller 클래스 입니다.
 *
 * @author : 송학현
 * @author : 최예린
 * @author : 김선홍
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final QueryMemberService queryMemberService;
    private final QueryCouponService queryCouponService;
    private final QueryPointHistoryService pointHistoryService;
    private final QueryProductService queryProductService;
    private final QueryWishlistService queryWishlistService;
    private final CookieUtils cookieUtils;
    private final ObjectMapper objectMapper;
    private static final String COOKIE = "recent";

    /**
     * 메인페이지를 반환시켜줍니다. 신간 상품과, 최근 본 상품 리스트를 가져옵니다.
     *
     * @return 메인페이지
     * @author 송학현
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping
    public String main(
            Model model,
            @CookieValue(required = false, name = COOKIE) Cookie recentViewProductList
    )
            throws JsonProcessingException {
        model.addAttribute(
                "recentProductList",
                queryProductService.findRecentProduct(PageRequest.of(0, 12))
        );
        Set<Long> recentViewSet = getRecentViewProductList(recentViewProductList);
        model.addAttribute(
                "recentViewProductList",
                sort(
                        recentViewSet,
                        queryProductService.findRecentViewProduct(
                                recentViewSet,
                                PageRequest.of(0, 10)
                        ).getDataList(),
                        PageRequest.of(0, 10)
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
        Set<Long> recentViewList = getRecentViewList(cookie, response);
        model.addAttribute("recentViewList",sort(
                recentViewList,
                queryProductService.findRecentViewProduct(
                                recentViewList,
                                PageRequest.of(0, 12)
                        )
                        .getDataList(),
                PageRequest.of(0, 12)
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

    private Set<Long> getRecentViewProductList(Cookie cookie) throws JsonProcessingException {
        if (Objects.nonNull(cookie)) {
            return objectMapper.readValue(
                    URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8),
                    new TypeReference<LinkedHashSet<Long>>() {
                    }
            );
        }
        return new LinkedHashSet<Long>();
    }

    private List<ProductRecentResponseDto> sort(
            Set<Long> recentViewSet,
            List<ProductRecentResponseDto> recentViewlist,
            Pageable pageable
    ) {
        List<Long> sort = new ArrayList<>(recentViewSet)
                .subList(
                        pageable.getPageSize() * pageable.getPageNumber(),
                        pageable.getPageSize() * pageable.getPageNumber() + recentViewlist.size()
                );
        List<ProductRecentResponseDto> list = new ArrayList<>();
        for (Long id : sort) {
            for (ProductRecentResponseDto productRecentResponseDto : recentViewlist) {
                if (productRecentResponseDto.getId().equals(id)) {
                    list.add(productRecentResponseDto);
                    break;
                }
            }
        }
        Collections.reverse(list);
        return list;
    }

    private Set<Long> getRecentViewList(Cookie cookie, HttpServletResponse response)
            throws JsonProcessingException {
        if (!Objects.isNull(cookie)) {
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

    private Cookie createCookie(Set<Long> value) throws JsonProcessingException {
        return cookieUtils.createCookie(
                COOKIE,
                URLEncoder.encode(objectMapper.writeValueAsString(value), StandardCharsets.UTF_8),
                259200
        );
    }
}
