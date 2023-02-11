package shop.yesaladin.front.product.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import java.util.Map;
import java.util.Objects;

/**
 * 상품 모든 사용자용 조회 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class ProductMainWebController {

    private final QueryProductService queryProductService;
    private final QueryProductTypeService queryProductTypeService;
    private static final String RECENT = "recent";
    private final ObjectMapper objectMapper;

    /**
     * [GET /products/{productId}] 상품 상세 조회 View를 반환합니다.
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
            HttpServletRequest request,
            HttpServletResponse httpServletResponse
    ) throws JsonProcessingException {
        ProductDetailResponseDto response = queryProductService.getProductDetail(productId);

        model.addAttribute(response);
//        checkCookie(request, httpServletResponse, productId);
        return "main/product/product";
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

    private void checkCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            Long productId
    )
            throws JsonProcessingException {
        log.info(productId + "");
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(RECENT)) {
                LinkedHashSet<Long> recent = objectMapper.readValue(
                        cookie.getValue(),
                        new TypeReference<LinkedHashSet<Long>>() {
                        }
                );
                recent.add(productId);
                cookie.setValue(objectMapper.writeValueAsString(recent));
                response.addCookie(cookie);
                return;
            }
        }
        Set<Long> set = new LinkedHashSet<>();
        set = Collections.synchronizedSet(set);
        set.add(productId);
        Cookie cookie = new Cookie(RECENT, objectMapper.writeValueAsString(set));
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(259200);
        response.addCookie(cookie);
    }
}
