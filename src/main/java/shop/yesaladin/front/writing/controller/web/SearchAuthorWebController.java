package shop.yesaladin.front.writing.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.writing.dto.SearchedAuthorResponseDto;
import shop.yesaladin.front.writing.service.inter.SearchAuthorService;

/**
 * 저자 관련 Rest Controller 자바스크립트 fetch 통신 컨트롤러
 *
 * @author : 김선홍
 * @since : 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/authors/search")
public class SearchAuthorWebController {

    private final SearchAuthorService searchAuthorService;

    /**
     * 이름으로 저자를 검색하는 메서드
     *
     * @param name   검색할 저자의 이름
     * @param offset 페이지 위치
     * @param size   데이터 갯수
     * @return 조회된 저자 리스트
     * @author : 김선홍
     * @since : 1.0
     */
    @GetMapping(params = "name")
    public SearchedAuthorResponseDto searchAuthorByName(String name, int offset, int size) {
        return searchAuthorService.searchAuthorByName(name, offset, size);
    }
}
