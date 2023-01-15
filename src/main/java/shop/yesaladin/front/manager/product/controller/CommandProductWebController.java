package shop.yesaladin.front.manager.product.controller;

import static java.util.UUID.randomUUID;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import shop.yesaladin.front.manager.file.dto.FileUploadResponseDto;
import shop.yesaladin.front.manager.file.service.inter.FileStorageService;
import shop.yesaladin.front.manager.file.service.inter.StorageAuthService;
import shop.yesaladin.front.manager.product.dto.ProductCreateRequestDto;
import shop.yesaladin.front.manager.product.dto.ProductRequestedDto;
import shop.yesaladin.front.manager.product.service.inter.QueryProductService;
import shop.yesaladin.front.manager.product.service.inter.CommandProductService;

/**
 * 상품 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/web/products")
public class CommandProductWebController {

    private final CommandProductService commandProductService;
    private final QueryProductService queryProductService;

    /**
     * [GET /web/products/form] 상품 등록 form view를 반환합니다.
     *
     * @param model 뷰로 데이터 전달
     * @return 상품 등록 form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/form")
    public String productForm(Model model) {
        Map<String, Object> productRelatedDtoMap = queryProductService.getProductRelatedDtoMap();

        model.addAllAttributes(productRelatedDtoMap);

        return "/manager/product/productForm";
    }

    /**
     * [POST /web/products] 상품 등록을 등록합니다.
     *
     * @return 등록된 상품의 상세 페이지
     * @author 이수정
     * @since 1.0
     */
    @PostMapping
    public String register(@ModelAttribute ProductRequestedDto productRequestedDto) {
        long id = commandProductService.register(productRequestedDto);

        return "redirect:/web/products/" + id;
    }

}
