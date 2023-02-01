package shop.yesaladin.front.writing.service.inter;

import shop.yesaladin.front.writing.dto.SearchedAuthorResponseDto;

/**
 * 엘라스틱서치 저자 검색 서비스 인터페이스
 *
 * @since : 1.0
 * @author : 김선홍
 */
public interface SearchAuthorService {
    SearchedAuthorResponseDto searchAuthorByName(String name, int offset, int size);
}
