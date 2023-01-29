package shop.yesaladin.front.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.product.dto.SearchProductRequestDto;
import shop.yesaladin.front.product.dto.SearchedProductResponseDto;
import shop.yesaladin.front.product.service.inter.SearchProductService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/search/products")
public class SearchProductWebController {

    private final SearchProductService searchProductService;
    private static final int BLOCK_SIZE = 3;

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
        return "/main/product/searched-products";
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
        model.addAttribute("start", start);
        model.addAttribute("last", last);
    }
}
