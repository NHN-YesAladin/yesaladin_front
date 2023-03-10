package shop.yesaladin.front.publish.controller.web;

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
import shop.yesaladin.front.publish.dto.PublishersResponseDto;
import shop.yesaladin.front.publish.service.inter.QueryPublisherService;

import java.util.Map;

/**
 * 춢판사 등록/조회 관련 관리자 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping
public class PublisherManagerWebController {

    private final QueryPublisherService queryPublisherService;

    /**
     * [GET /manager/publishers] 관리자용 출판사 전체 조회 View를 반환합니다.
     *
     * @param page  현재 페이지
     * @param size  페이지 크기
     * @param model 뷰로 데이터 전달
     * @return 관리자용 출판사 전체 조회 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/publishers")
    public String managerPublishers(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        PaginatedResponseDto<PublishersResponseDto> publishers = queryPublisherService.findAllForManager(
                new PageRequestDto(page, size)
        );

        Map<String, Object> pageInfoMap = getPageInfo(publishers);
        model.addAllAttributes(pageInfoMap);

        return "manager/publisher/publishers";
    }

    /**
     * @param model 뷰로 데이터 전달
     * @return 검색 결과 와 뷰
     * @author 김선홍
     * @since 1.0
     */
    @GetMapping(value = "/manager/publishers", params = "name")
    public String managerPublishers(
            @RequestParam(required = false) String name,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        PaginatedResponseDto<PublishersResponseDto> publishers = queryPublisherService.findByNameForManager(
                name,
                pageable
        );

        Map<String, Object> pageInfoMap = getPageInfo(publishers);
        model.addAllAttributes(pageInfoMap);
        model.addAttribute("selected", "name");
        model.addAttribute("input", name);

        return "manager/publisher/publishers";
    }

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param publishers 페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            PaginatedResponseDto<PublishersResponseDto> publishers
    ) {
        return Map.of(
                "totalPage", publishers.getTotalPage(),
                "currentPage", publishers.getCurrentPage(),
                "totalDataCount", publishers.getTotalDataCount(),
                "publishers", publishers.getDataList()
        );
    }
}
