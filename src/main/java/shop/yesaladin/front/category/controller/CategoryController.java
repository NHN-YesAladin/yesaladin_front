package shop.yesaladin.front.category.controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.category.dto.CategoryCreateRequest;

/**
 * 카테고리 관련 컨트롤러
 *
 * @author 배수한
 * @since 1.0
 */

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @PostMapping
    public String createCategory(@Valid @ModelAttribute CategoryCreateRequest createRequest) {
        // 서비스에서 createRequest를 이용해서 서버와 통신 하도록 함

        // html 경로를 return
        return null;
    }
}
