package shop.yesaladin.front.product.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.*;
import shop.yesaladin.front.product.service.inter.CommandProductService;
import shop.yesaladin.front.product.service.inter.ElasticCommandProductService;
import shop.yesaladin.front.product.service.inter.QueryProductService;
import shop.yesaladin.front.product.service.inter.QueryProductTypeService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 상품 등록/조회/수정/삭제 관련 관리자용 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class ProductManagerWebController {

    private final CommandProductService commandProductService;
    private final ElasticCommandProductService elasticCommandProductService;
    private final QueryProductService queryProductService;

    private final QueryProductTypeService queryProductTypeService;

    /**
     * [GET /manager/products/form] 상품 등록 form view를 반환합니다.
     *
     * @return 상품 등록 form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/products/form")
    public String productForm(Model model) {
        List<ProductTypeResponseDto> types = queryProductTypeService.findAll();

        model.addAttribute(types);

        return "manager/product/productForm";
    }

    /**
     * [POST /products] 상품을 등록합니다.
     *
     * @param createRequestDto 상품의 수정할 정보를 담고 있는 Dto
     * @return 등록된 상품의 상세 페이지
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/manager/products")
    public String register(@ModelAttribute ProductCreateRequestDto createRequestDto)
            throws IOException {
        commandProductService.register(createRequestDto);

        return "redirect:/manager/products";
    }

    /**
     * [GET /manager/products/{productId}] 상품 수정 form view를 반환합니다.
     *
     * @param productId 수정할 상품의
     * @param model     뷰로 데이터 전달
     * @return 상품 수정 form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/products/{productId}")
    public String productModifyForm(@PathVariable String productId, Model model) {
        ProductModifyInitDto modifyDto = queryProductService.getProductForForm(productId);
        List<ProductTypeResponseDto> types = queryProductTypeService.findAll();

        model.addAttribute(modifyDto);
        model.addAttribute(types);
        model.addAttribute(productId);

        return "manager/product/productModifyForm";
    }

    /**
     * [POST /products/{productId}] 특정 상품을 수정합니다.
     *
     * @param modifyRequestDto 상품의 수정할 정보를 담고 있는 Dto
     * @param productId        수정할 상품의 Id
     * @return 수정된 상품의 상세 페이지
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/manager/products/{productId}")
    public String modify(
            @ModelAttribute ProductModifyRequestDto modifyRequestDto,
            @PathVariable long productId
    ) throws IOException {
        commandProductService.modify(modifyRequestDto, productId);
        elasticCommandProductService.update(productId);

        return "redirect:/manager/products";
    }

    /**
     * [POST /products/{productId}] 특정 상품을 삭제합니다.
     *
     * @param productId 삭제할 상품의 Id
     * @param request   새로고침할 주소를 받아오기 위한 request
     * @return 새로고침
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/manager/products/{productId}/delete")
    public String softDelete(@PathVariable long productId, HttpServletRequest request) {
        commandProductService.softDelete(productId);
        elasticCommandProductService.delete(productId);
        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * [POST /products/{productId}/is-sale] 특정 상품의 판매여부를 변경합니다.
     *
     * @param productId 변경할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/manager/products/{productId}/is-sale")
    public String changeIsSale(@PathVariable long productId, HttpServletRequest request) {
        commandProductService.changeIsSale(productId);
        elasticCommandProductService.changeIsSale(productId);

        return "redirect:" + request.getHeader("Referer");
    }

    /**
     * [POST /products/{productId}/is-forced-out-of-stock] 특정 상품의 강제품절여부를 변경합니다.
     *
     * @param productId 변경할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/manager/products/{productId}/is-forced-out-of-stock")
    public String changeIsForcedOutOfStock(@PathVariable long productId, HttpServletRequest request) {
        commandProductService.changeIsForcedOutOfStock(productId);
        elasticCommandProductService.changeIsForcedOutOfStock(productId);

        return "redirect:" + request.getHeader("Referer");
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
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        model.addAttribute("size", size);
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findAllForManager(
                new PageRequestDto(page, size),
                typeId
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", Objects.isNull(typeId) ? "" : typeId,
                "types", queryProductTypeService.findAll(),
                "input", ""
        ));

        return "manager/product/products";
    }

    /**
     * 제목으로 상품검색
     *
     * @param title    검색할 상품의 제목
     * @param pageable 페이지 정보
     * @param model    뷰에 데이터 전달
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/products", params = "title")
    public String managerProductsByTitle(
            @RequestParam(required = false) String title,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        log.info(title);
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findByTitleForManager(
                title,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", "",
                "types", queryProductTypeService.findAll(),
                "selected", "title",
                "input", title
        ));

        return "manager/product/products";
    }

    /**
     * 내용으로 상품검색
     *
     * @param content  검색할 내용
     * @param pageable 페이지 정보
     * @param model    뷰에 데이터 전달
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/products", params = "content")
    public String managerProductsByContent(
            @RequestParam(required = false) String content,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findByContentForManager(
                content,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", "",
                "types", queryProductTypeService.findAll(),
                "selected", "content",
                "input", content
        ));

        return "manager/product/products";
    }

    /**
     * 출찬사명으로 상품검색
     *
     * @param publisher 검색할 상품의 저자
     * @param pageable  페이지 정보
     * @param model     뷰에 데이터 전달
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/products", params = "publisher")
    public String managerProductsByPublisher(
            @RequestParam(required = false) String publisher,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findByPublisherForManager(
                publisher,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", "",
                "types", queryProductTypeService.findAll(),
                "selected", "publisher",
                "input", publisher
        ));

        return "manager/product/products";
    }

    /**
     * 저자명으로 상품검색
     *
     * @param author   검색할 상품의 저자
     * @param pageable 페이지 정보
     * @param model    뷰에 데이터 전달
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/products", params = "author")
    public String managerProductsByAuthor(
            @RequestParam(required = false) String author,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findByAuthorForManager(
                author,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", "",
                "types", queryProductTypeService.findAll(),
                "selected", "author",
                "input", author
        ));

        return "manager/product/products";
    }

    /**
     * isbn으로 상품검색
     *
     * @param isbn     검색할 상품의 isbn
     * @param pageable 페이지 정보
     * @param model    뷰에 데이터 전달
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/products", params = "isbn")
    public String managerProductsByISBN(
            @RequestParam(required = false) String isbn,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<ProductsResponseDto> products = queryProductService.findByISBNForManager(
                isbn,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(products);
        model.addAllAttributes(pageInfoMap);

        model.addAllAttributes(Map.of(
                "products", products.getDataList(),
                "typeId", "",
                "types", queryProductTypeService.findAll(),
                "selected", "isbn",
                "input", isbn
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

}
