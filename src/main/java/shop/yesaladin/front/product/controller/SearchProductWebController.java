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
    String searchProduct(Model model, @ModelAttribute SearchProductRequestDto requestDto) {
        SearchedProductResponseDto response = searchProductService.searchProductsByProductField(
                requestDto);
        model.addAttribute("products", response.getProducts());
        getPageInfo(
                model,
                requestDto.getOffset(),
                requestDto.getSize(),
                response.getCount()
        );
        model.addAttribute("offset", requestDto.getOffset());
        model.addAttribute("selected", requestDto.getSelected());
        model.addAttribute("input", requestDto.getInput());
        model.addAttribute("blockSize", BLOCK_SIZE);
        return "main/product/searched-products";
    }

    @GetMapping("/categories")
    String searchProductByCategory(
            Model model,
            @RequestParam(name = "categoryid") Long categoryId,
            @PageableDefault Pageable pageable
    ) {
        SearchedProductResponseDto response = searchProductService.searchProductByCategoryId(
                categoryId,
                pageable
        );
        getPageInfo(model, pageable.getPageNumber(), pageable.getPageSize(), response.getCount());
        model.addAttribute("blockSize", BLOCK_SIZE);
        model.addAttribute("products", response.getProducts());
        model.addAttribute("categoriesid", categoryId);
        return "main/product/category-products";
    }

    private void getPageInfo(Model model, int offset, int size, Long count) {

        long totalPage = count % size == 0 ? count / size : count / size + 1;
        int block = (int) ((long) offset / BLOCK_SIZE);
        long start = (long) block * BLOCK_SIZE + 1;
        long last = Math.min((start + BLOCK_SIZE - 1), totalPage);
        if (start > last) {
            last = start;
        }

        log.info("size: " + size);
        log.info("totalPage: " + totalPage);
        log.info("currentPage: " + offset);
        log.info("start: " + start);
        log.info("last: " + last);

        model.addAttribute("size", size);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", offset);
        model.addAttribute("start", start);
        model.addAttribute("last", last);
    }
}
