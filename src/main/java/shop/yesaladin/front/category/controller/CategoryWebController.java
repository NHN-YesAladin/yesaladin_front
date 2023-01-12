package shop.yesaladin.front.category.controller;

import java.awt.print.Pageable;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.category.dto.CategoryCreateRequest;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.service.inter.QueryCategoryService;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;

/**
 * 카테고리 관련 컨트롤러
 *
 * @author 배수한
 * @since 1.0
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoryWebController {

    private final QueryCategoryService queryCategoryService;

    @GetMapping("/command")
    public String commandCategory(
            @RequestParam(name = "id", required = false) Long parentId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Model model
    ) {
        if (Objects.nonNull(parentId)) {
            PaginatedResponseDto<CategoryResponseDto> paginatedResponseDto = queryCategoryService.getChildCategoriesByParentId(
                    new PageRequestDto(page, size),
                    parentId
            );
            model.addAttribute("currentPage", paginatedResponseDto.getCurrentPage());
            model.addAttribute("totalPage", paginatedResponseDto.getTotalPage());
            model.addAttribute("totalDateCount", paginatedResponseDto.getTotalDataCount());
            model.addAttribute("children", paginatedResponseDto.getDataList());
        }
        model.addAttribute("parentCategories", queryCategoryService.getParentCategories());
        return "category/command-category";
    }



    @PostMapping
    public String createCategory(@Valid @ModelAttribute CategoryCreateRequest createRequest) {
        // 서비스에서 createRequest를 이용해서 서버와 통신 하도록 함

        // html 경로를 return
        return null;
    }
}
