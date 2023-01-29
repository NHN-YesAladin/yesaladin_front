package shop.yesaladin.front.publish.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.publish.dto.PublishersResponseDto;
import shop.yesaladin.front.publish.service.inter.QueryPublisherService;
import shop.yesaladin.front.writing.dto.AuthorsResponseDto;
import shop.yesaladin.front.writing.service.inter.QueryAuthorService;

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
     * @param page  현재 페이지 - 1
     * @param size  페이지 크기
     * @param model 뷰로 데이터 전달
     * @return 관리자용 출판사 전체 조회 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/manager/publishers")
    public String managerPublishers(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "3") Integer size,
            Model model
    ) {
        int blockSize = 5;

        PaginatedResponseDto<PublishersResponseDto> publishers = queryPublisherService.findAllForManager(
                new PageRequestDto(page, size)
        );

        Map<String, Object> pageInfoMap = getPageInfo(size, blockSize, publishers);
        model.addAllAttributes(pageInfoMap);

        model.addAttribute("publishers", publishers.getDataList());

        return "manager/publisher/publishers";
    }

    /**
     * Paging Bar에 필요한 정보를 계산하고 Map으로 저장하여 반환합니다.
     *
     * @param size      페이지에 들어갈 오브젝트 개수
     * @param blockSize 한 블럭에 들어갈 페이지 수
     * @param publishers   페이징된 정보를 담고있는 PaginatedResponseDto
     * @return Paging Bar에 필요한 정보를 담은 Map
     * @author 이수정
     * @since 1.0
     */
    private Map<String, Object> getPageInfo(
            int size,
            int blockSize,
            PaginatedResponseDto<PublishersResponseDto> publishers
    ) {
        long currentPage = publishers.getCurrentPage();
        long totalPage = publishers.getTotalPage();

        int block = (int) (currentPage / blockSize);
        long start = (long) block * blockSize + 1;
        long last = Math.min((start + blockSize - 1), totalPage);
        if (start > last) {
            last = start;
        }

        return Map.of(
                "size", size,
                "totalPage", totalPage,
                "currentPage", currentPage,
                "start", start,
                "last", last,
                "blockSize", blockSize
        );
    }
}
