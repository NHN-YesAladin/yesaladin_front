package shop.yesaladin.front.product.controller;

import lombok.RequiredArgsConstructor;
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
 * 상품 조회 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping
public class QueryProductWebController {

    private final QueryProductService queryProductService;
    private final QueryProductTypeService queryProductTypeService;

    /**
     * [GET /products/{productId}] 상품 상세 조회 View를 반환합니다.
     *
     * @param model 뷰로 데이터 전달
     * @return 상품 상세 조회 form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/products/{productId}")
    public String product(@PathVariable long productId, Model model) {
        ProductDetailResponseDto response = queryProductService.getProductDetail(productId);

        model.addAllAttributes(makeAttributeMap(response));

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
     * [GET /manager/products] 관리자용 상품 전체 조회 View를 반환합니다.
     *
     * @param typeId 지정한 상품 유형 Id(없으면 전체 유형)
     * @param page   현재 페이지 - 1
     * @param size   페이지 크기
     * @param model  뷰로 데이터 전달
     * @return 관리자용 상품 전체 조회 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/products")
    public String managerProducts(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            Model model
    ) {
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findAllForManager(
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

        return "manager/product/products";
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
     * 상세 조회로 응답받은 DTO를 바탕으로 Model에 Attribute로 추가할 정보를 담은 Map으로 반환합니다.
     *
     * @param response 상세 조회로 응답받은 DTO
     * @return Model에 Attribute로 추가할 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> makeAttributeMap(ProductDetailResponseDto response) {

        boolean isEbook = Objects.nonNull(response.getEbookFileUrl()) && !response.getEbookFileUrl()
                .isBlank();

        boolean onSale = response.getQuantity() > 0 && !response.isForcedOutOfStock() && response.isSale() && !response.isDeleted();

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
                Map.entry("description", response.getDescription()),
                Map.entry("onSale", onSale)
        );
    }
}
