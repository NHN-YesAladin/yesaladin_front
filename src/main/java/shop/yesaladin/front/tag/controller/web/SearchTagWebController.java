package shop.yesaladin.front.tag.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.tag.dto.SearchedTagsResponseDto;
import shop.yesaladin.front.tag.service.inter.SearchTagService;

/**
 * 태그 관련 Rest Controller 자바스크립트 fetch 통신 컨트롤러
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags/search")
public class SearchTagWebController {
    private final SearchTagService searchTagService;

    @GetMapping(params = "name")
    public SearchedTagsResponseDto searchTagByName(String name, int offset, int size) {
        return searchTagService.searchTagByName(name, offset, size);
    }
}
