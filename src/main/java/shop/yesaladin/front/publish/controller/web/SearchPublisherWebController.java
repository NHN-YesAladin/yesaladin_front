package shop.yesaladin.front.publish.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.publish.dto.SearchPublisherResponseDto;
import shop.yesaladin.front.publish.service.inter.SearchPublisherService;

/**
 * 출판사 관련 Rest Controller 자바스크립트 fetch 통신 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/publishers/search")
public class SearchPublisherWebController {

    private final SearchPublisherService searchPublisherService;

    @GetMapping(params = "name")
    public SearchPublisherResponseDto searchPublisherByName(String name, int offset, int size) {
        return searchPublisherService.searchPublisherByName(name, offset, size);
    }
}
