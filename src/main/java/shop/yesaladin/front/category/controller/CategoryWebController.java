package shop.yesaladin.front.category.controller;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
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
        return "category/command-category";
    }



    @PostMapping
    public String createCategory(@Valid @ModelAttribute CategoryCreateRequest createRequest) {
        // 서비스에서 createRequest를 이용해서 서버와 통신 하도록 함

        // html 경로를 return
        return null;
    }
}
