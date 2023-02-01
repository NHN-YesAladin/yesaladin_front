package shop.yesaladin.front.product.controller;

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
import shop.yesaladin.front.product.dto.RelationsResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.product.service.inter.QueryRelationService;

import java.util.Map;

/**
 * 상품 연관관계 조회 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class QueryRelationWebController {

    private final QueryRelationService queryRelationService;
    private final QueryProductService queryProductService;

    /**
     * [GET /manager/product/{productId}/relations] 관리자용 상품 연관관계 조회 View를 반환합니다.
     *
     * @param productId 연관관계를 조회할 상품의 Id
     * @param model     뷰로 데이터 전달
     * @return 상품 연관관계 View
     * @auhtor 이수정
     * @since 1.0
     */
    @GetMapping("/manager/products/{productId}/relations")
    public String productRelations(
            @PathVariable long productId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        ProductDetailResponseDto product = queryProductService.getProductDetail(productId);
        PaginatedResponseDto<RelationsResponseDto> relations = queryRelationService.getRelations(productId, new PageRequestDto(page, size));

        model.addAttribute("product", product);
        model.addAttribute("relations", relations.getDataList());

        Map<String, Object> pageInfoMap = getPageInfo(relations);
        model.addAllAttributes(pageInfoMap);

        return "manager/product/productRelations";
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
            PaginatedResponseDto<RelationsResponseDto> products
    ) {
        return Map.of(
                "totalPage", products.getTotalPage(),
                "currentPage", products.getCurrentPage(),
                "totalDataCount", products.getTotalDataCount(),
                "tags", products.getDataList()
        );
    }
}
