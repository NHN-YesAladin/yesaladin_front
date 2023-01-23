package shop.yesaladin.front.product.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

/**
 * 상품 조회 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
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

        return "/user/product/product";
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
            @RequestParam(required = false, defaultValue = "20") Integer size,
            Model model
    ) {
        int blockSize = 10;

        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findAll(
                new PageRequestDto(page, size),
                typeId
        );

        Map<String, Object> pageInfoMap = getPageInfo(size, blockSize, products);
        model.addAllAttributes(pageInfoMap);

        List<ProductsResponseDto> dataList = products.getDataList();
        dataList.forEach(ProductsResponseDto::makeAuthorLine);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", Objects.isNull(typeId) ? "" : typeId,
                "types", queryProductTypeService.findAll()
        ));

        return "/user/product/products";
    }

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param size      페이지에 들어갈 오브젝트 개수
     * @param blockSize 한 블럭에 들어갈 페이지 수
     * @param products  페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            int size,
            int blockSize,
            PaginatedResponseDto<ProductsResponseDto> products
    ) {
        long currentPage = products.getCurrentPage();
        long totalPage = products.getTotalPage();

        int block = (int) (currentPage / blockSize);
        long start = (long) block * blockSize + 1;
        long last = Math.min((start + blockSize - 1), totalPage);
        if (start > last) {
            last = start;
        }

        return Map.of(
                "size", size,
                "totalPage", products.getTotalPage(),
                "currentPage", products.getCurrentPage(),
                "start", start,
                "last", last,
                "blockSize", blockSize
        );
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
        int blockSize = 10;

        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findAll(
                new PageRequestDto(page, size),
                typeId
        );

        Map<String, Object> pageInfoMap = getPageInfo(size, blockSize, products);
        model.addAllAttributes(pageInfoMap);

        List<ProductsResponseDto> dataList = products.getDataList();
        dataList.stream().forEach(data -> data.makeAuthorLine());

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", Objects.isNull(typeId) ? "" : typeId,
                "types", queryProductTypeService.findAll()
        ));

        return "/manager/product/products";
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
