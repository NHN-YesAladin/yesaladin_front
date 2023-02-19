package shop.yesaladin.front.tag.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.tag.dto.TagsResponseDto;
import shop.yesaladin.front.tag.service.inter.QueryTagService;

import java.util.Map;

/**
 * 태그 등록/조회 관련 관리자 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping
public class TagManagerWebController {

    private final QueryTagService queryTagService;

    /**
     * [GET /manager/tags] 관리자용 태그 전체 조회 View를 반환합니다.
     *
     * @param page  현재 페이지
     * @param size  페이지 크기
     * @param model 뷰로 데이터 전달
     * @return 관리자용 태그 전체 조회 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/tags")
    public String managerTags(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        PaginatedResponseDto<TagsResponseDto> tags = queryTagService.findAllForManager(
                new PageRequestDto(page, size)
        );

        Map<String, Object> pageInfoMap = getPageInfo(tags);
        model.addAllAttributes(pageInfoMap);

        return "manager/tag/tags";
    }

    /**
     * 이름으로 태그를 검색하는 메서드
     *
     * @param name 검색할 이름
     * @param pageable 페이지 정보
     * @return 검색 결과
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/tags", params = "name")
    public String findByName(
            @RequestParam String name,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<TagsResponseDto> tags = queryTagService.findByNameForManager(
                name,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(tags);
        model.addAllAttributes(pageInfoMap);
        model.addAttribute("input", name);
        model.addAttribute("selected", "name");
        return "manager/tag/tags";
    }

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param tags 페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            PaginatedResponseDto<TagsResponseDto> tags
    ) {
        return Map.of(
                "totalPage", tags.getTotalPage(),
                "currentPage", tags.getCurrentPage(),
                "totalDataCount", tags.getTotalDataCount(),
                "tags", tags.getDataList()
        );
    }
}
