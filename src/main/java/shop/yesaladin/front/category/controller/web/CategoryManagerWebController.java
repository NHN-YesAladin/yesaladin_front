package shop.yesaladin.front.category.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
     * @param page     현재 페이지
     * @param size     페이징 사이즈
     * @param model
     * @return category/manager-categories.html
     */
    @GetMapping
    public String mangerCategories(
            @RequestParam(name = "id", required = false) Long parentId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Model model
    ) {
        // paging을 위한 초기 값 셋팅
        int initialPagingSize = 10;
        long currentPage = 1L;
        long totalPage = 1L;
        long start = 1L;
        long last = 1L;
        int blockSize = 5; // 페이징시 1~5 / 6~10과 같이 블럭 사이즈를 지정하는 변수
        List<CategoryResponseDto> dataList = new ArrayList<>();
        // size가 정해지지 않았을 경우, initialPagingSize 값을 따른다.
        model.addAttribute("size", size == null ? initialPagingSize : size);

        model.addAttribute("parentCategories", queryCategoryService.getParentCategories());
        model.addAttribute("id", parentId);

        if (Objects.nonNull(parentId)) {
            // 페이징 전용 dto를 리턴 받는다.
            PaginatedResponseDto<CategoryResponseDto> paginatedResponseDto = queryCategoryService.getChildCategoriesByParentId(
                    new PageRequestDto(page, size),
                    parentId
            );
            // dto에서 현재 페이지를 리턴하는 값을 변수에 저장
            currentPage = paginatedResponseDto.getCurrentPage();
            // dto에서 전체 페이지 개수의 값을 변수에 저장
            totalPage = paginatedResponseDto.getTotalPage();
            // dto에서 페이징된 데이터를 리스트에 저장
            dataList = paginatedResponseDto.getDataList();

            // 현재 블럭의 값 ex : block 1~5 = 0, block 6~10 = 1
            int block = (int) (currentPage / blockSize);
            // 블럭의 시작 지점 : 1부터 시작, block 변수 값에 따라 시작지점이 달라짐
            start = block * blockSize + 1;
            // 블럭의 마지막 지점 : 블럭의 마지막 값, blockSize의 배수 혹은 totalPage 값
            last = ((start + blockSize - 1) < totalPage) ? (start + blockSize - 1) : totalPage;

        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("dataList", dataList);

        model.addAttribute("start", start);
        model.addAttribute("last", last);
        model.addAttribute("blockSize", blockSize);
        return "manager/category/manager-categories";
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
            return "redirect:/manager/categories?id=" + responseDto.getId();
        }

        // html 경로를 return
        return "redirect:/manager/categories?id=" + responseDto.getParentId();
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

        CategoryResponseDto responseDto = commandCategoryService.modify(categoryId, modifyRequest);

        if (Objects.isNull(responseDto.getParentId())) {
            return "redirect:/manager/categories?id=" + responseDto.getId();
        }

        // html 경로를 return
        return "redirect:/manager/categories?id=" + responseDto.getParentId();
    }

    /**
     * 카테고리 삭제 처리 manager-categories.html 에서 javascript를 통해 페이지를 이동시킨다.
     *
     * @param categoryId 삭제하고자 하는 카테고리 id
     * @return redirect:/manager/categories
     */
    @PostMapping(params = "id")
    public String deleteCategory(@RequestParam("id") Long categoryId) {
        commandCategoryService.delete(categoryId);
        return "redirect:/manager/categories";
    }
}
