package shop.yesaladin.front.manager.product.controller;

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.manager.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.manager.product.service.inter.QueryProductService;

/**
 * 상품 조회 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/web/products")
public class QueryProductWebController {
    private final QueryProductService queryProductService;

    /**
     * [GET /web/products/{productId}] 상품 상세 조회 View를 반환합니다.
     *
     * @param model 뷰로 데이터 전달
     * @return 상품 상세 조회 form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/{productId}")
    public String product(@PathVariable long productId, Model model) {
        ProductDetailResponseDto response = queryProductService.getProductDetail(productId);

        model.addAllAttributes(makeAttributeMap(response));

        return "/user/product/productDetail";
    }

    /**
     * 상세 조회로 응답받은 DTO를 바탕으로 Model에 Attribute로 추가할 정보를 담은 Map으로 반환합니다.
     *
     * @param response 상세 조회로 응답받은 DTO
     * @return Model에 Attribute로 추가할 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private static Map<String, Object> makeAttributeMap(ProductDetailResponseDto response) {

        boolean isEbook = (Objects.nonNull(response.getEbookFileUrl()) && !response.getEbookFileUrl().isBlank()) ? true : false;

        return Map.ofEntries(
                Map.entry("id", response.getId()),
                Map.entry("isEbook", isEbook),
                Map.entry("title", response.getTitle()),
                Map.entry("authors", response.getAuthors()),
                Map.entry("publisher", response.getPublisher()),
                Map.entry("thumbnailFileUrl", response.getThumbnailFileUrl()),
                Map.entry("actualPrice", response.getActualPrice()),
                Map.entry("discountRate", response.getDiscountRate()),
                Map.entry("sellingPrice", response.getSellingPrice()),
                Map.entry("pointPrice", response.getPointPrice()),
                Map.entry("pointRate", response.getPointRate()),
                Map.entry("publishedDate", response.getPublishedDate()),
                Map.entry("isbn", response.getIsbn()),
                Map.entry("isSubscriptionAvailable", response.isSubscriptionAvailable()),
                Map.entry("issn", response.getIssn()),
                Map.entry("contents", response.getContents()),
                Map.entry("description", response.getDescription())
        );
    }

}
