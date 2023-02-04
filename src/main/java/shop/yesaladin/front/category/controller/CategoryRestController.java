package shop.yesaladin.front.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.dto.SearchedCategoryResponseDto;
import shop.yesaladin.front.category.service.inter.QueryCategoryService;
import shop.yesaladin.front.category.service.inter.SearchCategoryService;
import shop.yesaladin.front.common.dto.PageRequestDto;

/**
 * 카테고리 관련 Rest Controller 자바스크립트 fetch 통신을 보조하는 용으로 사용
 *
 * @author 배수한, 감선홍
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final QueryCategoryService queryCategoryService;
    private final SearchCategoryService searchCategoryService;

    /**
     * 1차 카테고리 전체를 조회하는 기능
     *
     * @return 카테고리 일부 정보 리스트
     */
    @GetMapping(params = "cate=parents")
    public List<CategoryResponseDto> getParentCategories() {
        return queryCategoryService.getParentCategories();
    }

    /**
     * 해당하는 1차 카테고리 id를 통해 2차 카테고리를 모두 조회하는 기능 페이징 size를 크게하여 조회함
     *
     * @param parentId
     * @return
     */
    @GetMapping(value = "/{parentId}", params = "cate=children")
    public List<CategoryResponseDto> getChildCategoriesByParentId(@PathVariable Long parentId) {
        return queryCategoryService.getChildCategoriesByParentId(
                new PageRequestDto(0, 200),
                parentId
        ).getDataList();
    }

    /**
     * 카테고리 이름으로 검색하는 메서드
     *
     * @param name   검색할 이름
     * @param offset 검색할 페이지 위치
     * @param size   총 데이터 갯수
     * @return 검색된 카테고리 리스트와 총 갯수
     */
    @GetMapping(value = "/search", params = {"name", "offset", "size"})
    public SearchedCategoryResponseDto searchCategoryByName(String name, int offset, int size) {
        return searchCategoryService.searchCategoryByName(name, offset, size);
    }
}
