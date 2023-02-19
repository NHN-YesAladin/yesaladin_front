package shop.yesaladin.front.writing.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.writing.dto.AuthorsResponseDto;
import shop.yesaladin.front.writing.service.inter.QueryAuthorService;

import java.util.Map;

/**
 * 저자 등록/조회 관련 관리자 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class AuthorManagerWebController {

    private final QueryAuthorService queryAuthorService;

    /**
     * [GET /manager/authors] 관리자용 저자 전체 조회 View를 반환합니다.
     *
     * @param page  현재 페이지
     * @param size  페이지 크기
     * @param model 뷰로 데이터 전달
     * @return 관리자용 저자 전체 조회 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/authors")
    public String managerAuthors(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        PaginatedResponseDto<AuthorsResponseDto> authors = queryAuthorService.findAllForManager(
                new PageRequestDto(page, size)
        );

        Map<String, Object> pageInfoMap = getPageInfo(authors);
        model.addAllAttributes(pageInfoMap);

        return "manager/author/authors";
    }

    /**
     * 로그인 아이디로 저자를 검색하는 메서드
     *
     * @param loginId 검색할 로그인 아이디
     * @param pageable 페이지 정보
     * @return 검색 결과 와 뷰
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/authors", params = "loginid")
    public String findByLoginId(
            @RequestParam(name = "loginid") String loginId,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<AuthorsResponseDto> authors = queryAuthorService.findByLoginIdForManager(loginId, pageable);
        Map<String, Object> pageInfoMap = getPageInfo(authors);
        model.addAllAttributes(pageInfoMap);
        model.addAttribute("selected", "loginid");
        model.addAttribute("input", loginId);

        return "manager/author/authors";
    }

    /**
     * 이름으로 저자를 검색하는 메서드
     *
     * @param name 검색할 이름
     * @param pageable 페이지 정보
     * @return 검색 결과 와 뷰
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/authors", params = "name")
    public String findByName(
            @RequestParam(name = "name") String name,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<AuthorsResponseDto> authors = queryAuthorService.findByNameForManager(name, pageable);
        Map<String, Object> pageInfoMap = getPageInfo(authors);
        model.addAllAttributes(pageInfoMap);
        model.addAttribute("selected", "loginid");
        model.addAttribute("input", name);

        return "manager/author/authors";
    }

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param authors 페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            PaginatedResponseDto<AuthorsResponseDto> authors
    ) {
        return Map.of(
                "totalPage", authors.getTotalPage(),
                "currentPage", authors.getCurrentPage(),
                "totalDataCount", authors.getTotalDataCount(),
                "authors", authors.getDataList()
        );
    }
}
