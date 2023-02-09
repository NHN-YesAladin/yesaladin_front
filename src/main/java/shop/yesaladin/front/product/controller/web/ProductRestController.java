package shop.yesaladin.front.product.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.RelationsResponseDto;
import shop.yesaladin.front.product.service.inter.QueryProductService;

/**
 * 연관 상품 등록을 위한 상품 검색 Rest 컨트롤러
 *
 * @author 김선홍
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ProductRestController {

    private final QueryProductService queryProductService;


    @GetMapping(value = "/manager/products/{productId}/relations", params = "title")
    public PaginatedResponseDto<RelationsResponseDto> findProductRelationByTitle(
            @PathVariable(name = "productId") Long id,
            String title,
            Pageable pageable
    ) {
        return queryProductService.findProductByTitle(id, title, pageable);
    }
}
