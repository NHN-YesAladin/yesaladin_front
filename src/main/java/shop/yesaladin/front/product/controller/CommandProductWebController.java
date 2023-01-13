package shop.yesaladin.front.product.controller;

import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
import shop.yesaladin.front.product.dto.ProductCreateDto;
import shop.yesaladin.front.product.dto.ProductTypeResponseDto;
import shop.yesaladin.front.product.dto.TagResponseDto;
import shop.yesaladin.front.writing.dto.AuthorResponseDto;
import shop.yesaladin.front.publish.dto.PublisherResponseDto;
import shop.yesaladin.front.product.service.inter.CommandProductService;

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
    private final RestTemplate restTemplate;

    @Value("${yesaladin.gateway}")
    private String url;

    /**
     * [GET /web/products/register] 상품 등록 Form을 view로 반환해줍니다.
     *
     * @return 상품 등록 Form
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/register")
    public String productForm(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<List<AuthorResponseDto>> authorResponse = restTemplate.exchange(
                url + "/v1/authors",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<AuthorResponseDto>>() {}
        );
        model.addAttribute("authors", authorResponse.getBody());

        ResponseEntity<List<PublisherResponseDto>> publisherResponse = restTemplate.exchange(
                url + "/v1/publishers",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<PublisherResponseDto>>() {}
        );
        model.addAttribute("publishers", publisherResponse.getBody());

        ResponseEntity<List<ProductTypeResponseDto>> productTypeResponse = restTemplate.exchange(
                url + "/v1/product-types",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<ProductTypeResponseDto>>() {}
        );
        model.addAttribute("types", productTypeResponse.getBody());

        ResponseEntity<List<TagResponseDto>> tagResponse = restTemplate.exchange(
                url + "/v1/tags",
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<TagResponseDto>>() {}
        );
        model.addAttribute("tags", tagResponse.getBody());

        return "/manager/product/productForm";
    }

    /**
     * [POST /web/products/register] 상품 등록을 등록합니다.
     *
     * @return 등록된 상품의 상세 페이지
     * @author 이수정
     * @since 1.0
     */
    @PostMapping("/register")
    public String register(
            @ModelAttribute ProductCreateDto productCreateDto
    ) {
        log.info("ISBN: " + productCreateDto.getIsbn());
        log.info("Thumbnail File: " + productCreateDto.getThumbnailFile());
        log.info("Title: " + productCreateDto.getTitle());
        log.info("Contents: " + productCreateDto.getContents());
        log.info("Description: " + productCreateDto.getDescription());
        log.info("Ebook File: " + productCreateDto.getEbookFile());
        log.info("Authors: " + productCreateDto.getAuthors());

        return "/manager/product/productForm";
    }

}
