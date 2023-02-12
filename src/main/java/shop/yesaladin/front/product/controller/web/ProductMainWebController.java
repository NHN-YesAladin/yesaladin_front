package shop.yesaladin.front.product.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.ProductsResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.product.service.inter.QueryProductTypeService;
import shop.yesaladin.front.wishlist.service.inter.CommandWishlistService;

/**
 * 상품 모든 사용자용 조회 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @author 김선홍
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class ProductMainWebController {

    private final QueryProductService queryProductService;
    private final QueryProductTypeService queryProductTypeService;
    private final CommandWishlistService commandWishlistService;
    private static final String DETAIL_VIEW = "main/product/product";
    private static final String RECENT = "recent";
    private static final String WISHLIST = "isWishlist";
    private final ObjectMapper objectMapper;

    /**
     * [GET /products/{productId}] 상품 상세 조회 View를 반환합니다.
     * 최근 본 상품 리스트에 productid 를 추가합니다.
     * 위시리스트에 해당 상품이 등록되어있는지 확인합니다.
     *
     * @param model 뷰로 데이터 전달
     * @return 상품 상세 조회 form
     * @author 이수정
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping("/products/{productId}")
    public String product(
            @PathVariable long productId,
            Model model,
            @CookieValue(name = "recent", required = false) Cookie recent,
            HttpServletResponse httpServletResponse,
            Authentication authentication
    ) throws JsonProcessingException {
        ProductDetailResponseDto response = queryProductService.getProductDetail(productId);
        model.addAttribute(response);
        if (Objects.nonNull(authentication)) {
            model.addAttribute(WISHLIST, commandWishlistService.isExist(productId));
        } else {
            model.addAttribute(WISHLIST, false);
        }
        checkCookieValue(recent, httpServletResponse, productId);
        return DETAIL_VIEW;
    }

    /**
     * [GET /products] 모든 사용자용 상품 전체 조회 View를 반환합니다.
     *
     * @param typeId 지정한 상품 유형 Id(없으면 전체 유형)
     * @param page   현재 페이지 - 1
     * @param size   페이지 크기
     * @param model  뷰로 데이터 전달
     * @return 모든 사용자용 상품 전체 조회 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            Model model
    ) {
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findAll(
                new PageRequestDto(page, size),
                typeId
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", Objects.isNull(typeId) ? "" : typeId,
                "types", queryProductTypeService.findAll()
        ));

        return "main/product/products";
    }

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param products 페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            PaginatedResponseDto<ProductsResponseDto> products
    ) {
        return Map.of(
                "totalPage", products.getTotalPage(),
                "currentPage", products.getCurrentPage(),
                "totalDataCount", products.getTotalDataCount(),
                "tags", products.getDataList()
        );
    }

    /**
     * 최근 본 상품 쿠키의 값을 확인(없을 경우 생성) 쿠키에 Set 을 문자열로 parse 후 인코딩 후 쿠키 값에 set 함
     *
     * @param recent    최근 본 상품이 담겨 있는 쿠키
     * @param response  쿠키를 담을 HttpServletResponse
     * @param productId 현재 상세 페이지로 넘어가는 상품의 id
     * @throws JsonProcessingException 최근 본 상품 list 의 json 파싱 오류
     * @author 김선홍
     * @since 1.0
     */
    private void checkCookieValue(Cookie recent, HttpServletResponse response, Long productId)
            throws JsonProcessingException {
        if (recent.getName().equals(RECENT)) {
            Set<Long> recentViewProductList = objectMapper.readValue(
                    URLDecoder.decode(recent.getValue(), StandardCharsets.UTF_8),
                    new TypeReference<LinkedHashSet<Long>>() {
                    }
            );
            recentViewProductList.remove(productId);
            recentViewProductList.add(productId);
            response.addCookie(createCookie(recentViewProductList));
            return;
        }
        Set<Long> recentViewProductList = new LinkedHashSet<>();
        recentViewProductList = Collections.synchronizedSet(recentViewProductList);
        recentViewProductList.add(productId);
        response.addCookie(createCookie(recentViewProductList));
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
        Cookie cookie = new Cookie(
                RECENT,
                URLEncoder.encode(objectMapper.writeValueAsString(value), StandardCharsets.UTF_8)
        );
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(259200);
        return cookie;
    }
}
