package shop.yesaladin.front.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.SearchProductRequestDto;
import shop.yesaladin.front.product.dto.SearchedProductResponseDto;
import shop.yesaladin.front.product.service.inter.SearchProductService;

/**
 * 상품 검색 컨트롤러
 *
 * @author 김선홍
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/search/products")
public class SearchProductWebController {

    private final SearchProductService searchProductService;
    private static final int BLOCK_SIZE = 3;

    /**
     * 상품의 제목, 내용, 저자, 출판사 등등으로 상품 검색하는 메서드
     *
     * @param model      검색 데이터를 반영할 model
     * @param requestDto 요청 dto
     * @return 검색된 상품 리스트
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping
    public String searchProduct(
            Model model,
            @ModelAttribute SearchProductRequestDto requestDto,
            @PageableDefault Pageable pageable
    ) {
        PaginatedResponseDto<SearchedProductResponseDto> response = searchProductService.searchProductsByProductField(
                requestDto,
                pageable
        );
        getDefaultInfo(model, response);
        model.addAttribute("selected", requestDto.getSelected());
        model.addAttribute("input", requestDto.getInput());
        model.addAttribute("size", pageable.getPageSize());
        return "main/product/searched-products";
    }

    @GetMapping("/categories")
    public String searchProductByCategory(
            Model model,
            @RequestParam(name = "categoryid") Long categoryId,
            @PageableDefault Pageable pageable
    ) {
        PaginatedResponseDto<SearchedProductResponseDto> response = searchProductService.searchProductByCategoryId(
                categoryId,
                pageable
        );
        getDefaultInfo(model, response);
        model.addAttribute("categoriesid", categoryId);
        return "main/product/category-products";
    }

    private void getDefaultInfo(Model model, PaginatedResponseDto<SearchedProductResponseDto> dto) {
        model.addAttribute("totalPage", dto.getTotalPage());
        model.addAttribute("currentPage", dto.getCurrentPage());
        model.addAttribute("totalDataCount", dto.getTotalDataCount());
        model.addAttribute("products", dto.getDataList());
        model.addAttribute("url", "/search/products");
    }
}
