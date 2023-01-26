package shop.yesaladin.front.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.service.inter.QueryCategoryService;
import shop.yesaladin.front.common.dto.PageRequestDto;

/**
 * 카테고리 관련 Rest Controller 자바스크립트 fetch 통신을 보조하는 용으로 사용
 *
 * @author 배수한
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final QueryCategoryService queryCategoryService;

    @GetMapping(params = "cate=parents")
    public List<CategoryResponseDto> getParentCategories() {
        return queryCategoryService.getParentCategories();
    }

    @GetMapping(value = "/{parentId}", params = "cate=children")
    public List<CategoryResponseDto> getChildCategories(@PathVariable Long parentId) {
        return queryCategoryService.getChildCategoriesByParentId(
                new PageRequestDto(0, 200),
                parentId
        ).getDataList();
    }
}
