package shop.yesaladin.front.wishlist.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.utils.CookieUtils;
import shop.yesaladin.front.product.dto.ProductRecentResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.wishlist.dto.WishlistResponseDto;
import shop.yesaladin.front.wishlist.service.inter.CommandWishlistService;
import shop.yesaladin.front.wishlist.service.inter.QueryWishlistService;

/**
 * 위시리스트, 최근 본 상품 컨트롤러
 *
 * @author 김선홍
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/interest")
public class WishWebController {

    private final QueryProductService queryProductService;
    private final CommandWishlistService wishlistService;
    private final QueryWishlistService queryWishlistService;
    private final ObjectMapper objectMapper;
    private static final String VIEW = "mypage/product/interest-product";
    private static final String RECENT = "recent";
    private static final String RECENTVIEWLIST = "recentViewList";
    private static final String WISHLIST = "wishlist";
    private static final String CURRENTPAGE = "currentPage";
    private static final String TOTALPAGE = "totalPage";
    private static final String URL = "url";
    private final CookieUtils cookieUtils;

    /**
     * 마이페이지의 위시리스트 페이지로 이동하면서 필요한 리스트를 받음
     *
     * @param cookie   최근 본 상품 리스트에 대한 정보가 담겨있는 리스트
     * @param response 쿠키를 저장할 response
     * @return 뷰와 리스트들
     * @throws JsonProcessingException json 파싱 실패 exception
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping
    public ModelAndView memberWishList(
            @CookieValue(name = RECENT, required = false) Cookie cookie,
            HttpServletResponse response
    ) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        Set<Long> recentViewList = getRecentViewList(cookie, response);
        modelAndView.addObject(
                RECENTVIEWLIST,
                queryProductService.findRecentViewProduct(recentViewList, PageRequest.of(0, 10))
                        .getDataList()
        );
        modelAndView.addObject(
                WISHLIST,
                queryWishlistService.getWishlist(PageRequest.of(0, 10)).getDataList()
        );
        return modelAndView;
    }

    /**
     * 최근 본 상품 더보기 페이지로 이동
     *
     * @param pageable 페이지 정보
     * @param cookie   최근 본 상품이 담겨 있는 쿠키
     * @param response 쿠키를 담을 response
     * @return 뷰와 최근 본 상품 리스트
     * @throws JsonProcessingException 파싱 오류
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/recent")
    public ModelAndView recentView(
            @PageableDefault Pageable pageable,
            @CookieValue(name = RECENT, required = false) Cookie cookie,
            HttpServletResponse response
    ) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("mypage/product/recent-view-products");
        Set<Long> recentViewList = getRecentViewList(cookie, response);
        PaginatedResponseDto<ProductRecentResponseDto> result = queryProductService.findRecentViewProduct(
                recentViewList,
                pageable
        );
        modelAndView.addObject(CURRENTPAGE, result.getCurrentPage());
        modelAndView.addObject(TOTALPAGE, result.getTotalPage());
        modelAndView.addObject(URL, "/interest/recent");
        modelAndView.addObject(RECENTVIEWLIST, result.getDataList());
        return modelAndView;
    }

    /**
     * 위시리스트 더보기 화면으로 이동
     *
     * @param pageable 페이지 정보
     * @return 위시리스트와 뷰
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/wishlist",params = "page")
    ModelAndView wishlistView(@PageableDefault Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("mypage/product/wishlist");
        PaginatedResponseDto<WishlistResponseDto> result = queryWishlistService.getWishlist(pageable);
        log.info(result.getCurrentPage() + "");
        modelAndView.addObject(CURRENTPAGE, result.getCurrentPage());
        modelAndView.addObject(TOTALPAGE, result.getTotalPage());
        modelAndView.addObject(URL, "/interest/wishlist");
        modelAndView.addObject(WISHLIST, result.getDataList());
        return modelAndView;
    }

    /**
     * 상품 상세페이지에서 위시리스트를 등록하는 메서드
     *
     * @param productId 삭제할 상품의 위시리스트
     * @return 상품의 상세페이지
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/{productid}")
    public String registerWishlist(@PathVariable(name = "productid") Long productId) {
        if (Boolean.FALSE.equals(wishlistService.isExist(productId))) {
            wishlistService.save(productId);
        }
        return "redirect:/products/" + productId;
    }

    /**
     * 상품 상세페이지에서 위시리스트를 삭제하는 메서드
     *
     * @param productId 삭제할 상품의 위시리스트
     * @return 상품의 상세페이지
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/delete")
    public String deleteWishlistInProductDetail(@RequestParam(name = "productid") Long productId) {
        if (Boolean.TRUE.equals(wishlistService.isExist(productId))) {
            wishlistService.delete(productId);
        }
        return "redirect:/products/" + productId;
    }

    /**
     * 관심상품 뷰에서 위시리스트 삭제 후 화면 관심상품 뷰로 이동
     *
     * @param productId 삭제할 상품의 id
     * @return 화면 상세페이지
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/mypage/delete")
    public ModelAndView deleteWishlistInInterestProduct(
            @CookieValue(name = RECENT, required = false) Cookie cookie,
            HttpServletResponse response,
            @RequestParam(name = "productid") Long productId
    ) throws JsonProcessingException {
        wishlistService.delete(productId);

        ModelAndView modelAndView = new ModelAndView(VIEW);
        Set<Long> recentViewList = getRecentViewList(cookie, response);
        modelAndView.addObject(
                RECENTVIEWLIST,
                queryProductService.findRecentViewProduct(recentViewList, PageRequest.of(0, 10))
                        .getDataList()
        );
        modelAndView.addObject(
                WISHLIST,
                queryWishlistService.getWishlist(PageRequest.of(0, 10)).getDataList()
        );
        return modelAndView;
    }

    /**
     * 관심상품 뷰에서 최근 본 상품을 삭제
     *
     * @param cookie    최근 본 상품 리스트에 대한 정보가 담겨있는 리스트
     * @param response  쿠키를 저장할 response
     * @param productId 삭제할 최근 본 상품의 id
     * @return 삭제 후 리스트와 뷰
     * @throws JsonProcessingException json 파싱 실패 exception
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/recentview")
    public ModelAndView deleteRecentViewProduct(
            @CookieValue(name = RECENT, required = false) Cookie cookie,
            HttpServletResponse response,
            @RequestParam(name = "productid") Long productId
    ) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView(VIEW);
        Set<Long> recentViewList = deleteRecentViewProductByProductId(cookie, response, productId);
        modelAndView.addObject(
                RECENTVIEWLIST,
                queryProductService.findRecentViewProduct(recentViewList, PageRequest.of(0, 10))
                        .getDataList()
        );
        modelAndView.addObject(
                WISHLIST,
                queryWishlistService.getWishlist(PageRequest.of(0, 10)).getDataList()
        );
        return modelAndView;
    }

    /**
     * 최근 본 상품 더보기 뷰에서 삭제할 경우
     *
     * @param cookie    최근 본 상품이 담겨 있는 쿠키
     * @param response  수정한 쿠키를 담을 HttpServletResponse
     * @param productId 삭제할 상품의 id
     * @param pageable  페이지 정보
     * @return 해당 페이지로 다시 이동
     * @throws JsonProcessingException 파싱 오류
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/recent/delete")
    public ModelAndView deleteRecentViewProductInRecentViewProduct(
            @CookieValue(name = RECENT, required = false) Cookie cookie,
            HttpServletResponse response,
            @RequestParam(name = "productid") Long productId,
            @PageableDefault Pageable pageable
    ) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("mypage/product/recent-view-products");
        Set<Long> recentViewList = deleteRecentViewProductByProductId(cookie, response, productId);
        PaginatedResponseDto<ProductRecentResponseDto> result = queryProductService.findRecentViewProduct(
                recentViewList,
                pageable
        );
        modelAndView.addObject(CURRENTPAGE, result.getCurrentPage());
        modelAndView.addObject(TOTALPAGE, result.getTotalPage());
        modelAndView.addObject(URL, "/interest/recent");
        modelAndView.addObject(RECENTVIEWLIST, result.getDataList());
        return modelAndView;
    }

    @GetMapping("/wishlist/delete")
    public ModelAndView deleteWishlistInWishlistView(
            @RequestParam(name = "productid") Long productId,
            @PageableDefault Pageable pageable
    ) {
        wishlistService.delete(productId);
        ModelAndView modelAndView = new ModelAndView("mypage/product/wishlist");
        PaginatedResponseDto<WishlistResponseDto> result = queryWishlistService.getWishlist(pageable);
        modelAndView.addObject(CURRENTPAGE, result.getCurrentPage());
        modelAndView.addObject(TOTALPAGE, result.getTotalPage());
        modelAndView.addObject("url", "/interest/wishlist");
        modelAndView.addObject(WISHLIST, result.getDataList());
        return modelAndView;
    }

    /**
     * 쿠키에 최근 본 상품 리스트를 얻는다. 없다면 리스트를 만들어 준다.
     *
     * @param cookie   최근 본 상품 리스트를 담고 있는 쿠키
     * @param response 쿠키를 저장할 HttpServletResponse
     * @return 최근 본 상품 리스트
     * @throws JsonProcessingException json 파싱 실패 exception
     * @author 김선홍
     * @since 1.0
     */
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

    /**
     * 최근 본 상품 삭제 메서드, 리스트에 삭제할 메서드가 없다면 아무일도 이러나지 않는다. 만약 쿠키에 리스트가 없다면 리스트를 만들어준다.
     *
     * @param cookie    최근 본 상품 리스트를 담고 있는 쿠키
     * @param response  쿠키를 저장할 HttpServletResponse
     * @param productId 삭제할 상품의 id
     * @return 삭제 후 최근 본 상품 리스트
     * @throws JsonProcessingException json 파싱 실패 exception
     * @author 김선홍
     * @since 1.0
     */
    private Set<Long> deleteRecentViewProductByProductId(
            Cookie cookie,
            HttpServletResponse response,
            Long productId
    )
            throws JsonProcessingException {
        if (Objects.nonNull(cookie.getValue())) {
            Set<Long> recentViewList = objectMapper.readValue(
                    URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8),
                    new TypeReference<LinkedHashSet<Long>>() {
                    }
            );
            recentViewList.remove(productId);
            response.addCookie(createCookie(recentViewList));
            return recentViewList;
        }
        Set<Long> recentViewList = new LinkedHashSet<>();
        response.addCookie(createCookie(recentViewList));
        return recentViewList;
    }

    /**
     * 쿠키 생성 메서드
     *
     * @param value 최근 본 상품이 담겨 있는 리스트
     * @return 최근 본 상품 리스트가 담겨 있는 쿠키
     * @throws JsonProcessingException 최근 본 상품 list 의 json 파싱 오류
     * @author 김선홍
     * @since 1.0
     */
    private Cookie createCookie(Set<Long> value) throws JsonProcessingException {
        return cookieUtils.createCookie(
                RECENT,
                URLEncoder.encode(objectMapper.writeValueAsString(value), StandardCharsets.UTF_8),
                259200
        );
    }
}
