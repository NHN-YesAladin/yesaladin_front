package shop.yesaladin.front.product.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.product.dto.ProductResponseDto;
import shop.yesaladin.front.product.service.inter.CommandProductService;
import shop.yesaladin.front.product.service.inter.QueryProductService;

/**
 * 상품 등록/수정/삭제 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class CommandProductWebController {

    private final CommandProductService commandProductService;
    private final QueryProductService queryProductService;

    /**
     * [GET /products/form] 상품 등록 form view를 반환합니다.
     *
     * @param model 뷰로 데이터 전달
     * @return 상품 등록 form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/products/form")
    public String productForm(Model model) {
        Map<String, Object> productRelatedDtoMap = queryProductService.getProductRelatedDtoMap();

        model.addAllAttributes(productRelatedDtoMap);

        return "/manager/product/productForm";
    }

    /**
     * [POST /products] 상품 등록을 등록합니다.
     *
     * @param productResponseDto 상품의 수정할 정보를 담고 있는 Dto
     * @return 등록된 상품의 상세 페이지
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/products")
    public String register(@ModelAttribute ProductResponseDto productResponseDto)
            throws IOException {
        long id = commandProductService.register(productResponseDto);

        return "redirect:/products/" + id;
    }

    // 상품 수정 미완
//    /**
//     * [GET /products/{productId}/form] 상품 수정 form view를 반환합니다.
//     *
//     * @param productId 수정할 상품의
//     * @param model 뷰로 데이터 전달
//     * @return 상품 수정 form
//     * @author 이수정
//     * @since 1.0
//     */
//    @GetMapping("/manager/products/{productId}")
//    public String productModifyForm(@PathVariable String productId, Model model) {
//        // Map<String, Object> 원래 있던 정보를 받아음.
//
//        // model.addAllAttributes(productRelatedDtoMap);
//
//        return "/manager/product/productModifyForm";
//    }
//
//    /**
//     * [PUT /products/{productId}] 특정 상품을 수정합니다.
//     *
//     * @param productResponseDto 상품의 수정할 정보를 담고 있는 Dto
//     * @param productId 수정할 상품의 Id
//     * @return 수정된 상품의 상세 페이지
//     * @author 이수정
//     * @since 1.0
//     */
//    @PutMapping("/products/{productId}")
//    public String modify(
//            @ModelAttribute ProductResponseDto productResponseDto,
//            @PathVariable long productId
//    ) {
//        commandProductService.modify(productResponseDto, productId);
//
//        return "redirect:/products/" + productId;
//    }

    /**
     * [DELETE /products/{productId}] 특정 상품을 삭제합니다.
     *
     * @param productId 삭제할 상품의 Id
     * @param request   새로고침할 주소를 받아오기 위한 request
     * @return 새로고침
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/products/{productId}")
    public String softDelete(@PathVariable long productId, HttpServletRequest request) {
        commandProductService.softDelete(productId);

        return "redirect:" + request.getHeader("Referer");
    }


}
