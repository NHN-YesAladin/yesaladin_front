package shop.yesaladin.front.category.controller.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.dto.CategorySaveRequestDto;
import shop.yesaladin.front.category.service.inter.CommandCategoryService;
import shop.yesaladin.front.category.service.inter.QueryCategoryService;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.exception.ValidationFailedException;

/**
 * 카테고리 관련 컨트롤러
 *
 * @author 배수한
 * @since 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/manager/categories")
public class CategoryManagerWebController {

    private final QueryCategoryService queryCategoryService;
    private final CommandCategoryService commandCategoryService;

    /**
     * manger-categories.html 을 보여주기 위한 컨트롤러 메서드 페이징된 2차 카테고리 리스트 제공과 생성,수정,삭제를 위한 기반 화면이다.
     *
     * @param parentId 페이징된 2차 카테고리 리스트를 보기 위해서는 1차 카테고리의 id값이 있어야한다
     * @param pageable 페이징 처리
     * @param model
     * @return category/manager-categories.html
     */
    @GetMapping
    public String mangerCategories(
            @RequestParam(name = "code", required = false) Long parentId,
            Pageable pageable,
            Model model
    ) {

        List<CategoryResponseDto> parentResponse = queryCategoryService.getParentCategories();
        model.addAttribute("parentCategories", parentResponse);

        Optional<CategoryResponseDto> parentOptional;
        if (Objects.isNull(parentId)) {
            parentOptional = Optional.ofNullable(parentResponse.get(0));

        } else {
            parentOptional = parentResponse.stream()
                    .filter(parent -> parent.getId().equals(parentId))
                    .findAny();
        }

        if (parentOptional.isEmpty()) {
            model.addAttribute("parent", null);

            model.addAttribute("code", null);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPage", 1);
            model.addAttribute("totalDataCount", 0);
            model.addAttribute("dataList", Collections.emptyList());
            return "manager/category/manager-categories";
        }

        CategoryResponseDto responseDto = parentOptional.get();
        model.addAttribute("parent", responseDto);

        Long parentIdView = responseDto.getId();
        PaginatedResponseDto<CategoryResponseDto> response = queryCategoryService.getChildCategoriesByParentId(
                pageable,
                parentIdView
        );

        model.addAttribute("code", parentIdView);
        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());
        return "manager/category/manager-categories";
    }


    @GetMapping("/order")
    public String changeOrders() {
        return "manager/category/manager-change-categories-order";
    }


    /**
     * 카테고리 생성 처리
     *
     * @param createRequest 이름, 노출여부, 부모id가 있는 dto
     * @param bindingResult
     * @return redirect:/manager/categories?id={부모id}
     */
    @PostMapping
    public String registerCategory(
            @Valid CategorySaveRequestDto createRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        CategoryResponseDto responseDto = commandCategoryService.create(createRequest);

        if (Objects.isNull(responseDto.getParentId())) {
            return "redirect:/manager/categories?code=" + responseDto.getId();
        }

        // html 경로를 return
        return "redirect:/manager/categories?code=" + responseDto.getParentId();
    }

    /**
     * 카테고리 수정 처리
     *
     * @param categoryId    수정하고자 하는 카테고리의 id
     * @param modifyRequest 이름, 노출여부, 부모id가 있는 dto
     * @param bindingResult
     * @return redirect:/manager/categories?id={부모id}
     */
    @PostMapping("/{categoryId}")
    public String modifyCategory(
            @PathVariable Long categoryId,
            @Valid CategorySaveRequestDto modifyRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        log.info("{}", modifyRequest);
        CategoryResponseDto responseDto = commandCategoryService.modify(categoryId, modifyRequest);

        if (Objects.isNull(responseDto.getParentId())) {
            return "redirect:/manager/categories?code=" + responseDto.getId();
        }

        // html 경로를 return
        return "redirect:/manager/categories?code=" + responseDto.getParentId();
    }

    /**
     * 카테고리 삭제 처리 manager-categories.html 에서 javascript를 통해 페이지를 이동시킨다.
     *
     * @param categoryId 삭제하고자 하는 카테고리 id
     * @return redirect:/manager/categories
     */
    @PostMapping(params = "deletedId")
    public String deleteCategory(@RequestParam("deletedId") Long categoryId) {
        commandCategoryService.delete(categoryId);
        return "redirect:/manager/categories";
    }
}
