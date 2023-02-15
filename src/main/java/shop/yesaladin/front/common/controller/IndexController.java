package shop.yesaladin.front.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;import shop.yesaladin.front.member.dto.MemberGrade;
import shop.yesaladin.front.member.dto.MemberStatisticsResponseDto;
import shop.yesaladin.front.member.service.inter.QueryMemberService;
import shop.yesaladin.front.point.service.inter.QueryPointHistoryService;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.statistics.dto.PercentageResponseDto;

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
    private final QueryPointHistoryService pointHistoryService;
    private final QueryProductService queryProductService;
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
//        model.addAttribute(
//                "recentProductList",
//                queryProductService.findRecentProduct(PageRequest.of(0, 12)).getDataList()
//        );
//        model.addAttribute(
//                "recentViewProductList",
//                queryProductService.findRecentViewProduct(getRecentViewProductList(
//                        recentViewProductList), PageRequest.of(0, 10)).getDataList()
//        );
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
    public String mypage(Model model) {
        long point = pointHistoryService.getMemberPoint();
        MemberGrade grade = MemberGrade.valueOf(queryMemberService.getMemberGrade());

        model.addAttribute("point", point);
        model.addAttribute("grade", grade);

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
}
