package shop.yesaladin.front.writing.service.inter;

import shop.yesaladin.front.writing.dto.SearchedAuthorResponseDto;

/**
 * 엘라스틱서치 저자 검색 서비스 인터페이스
 *
 * @since : 1.0
 * @author : 김선홍
 */
public interface SearchAuthorService {

    /**
     * 저자 이름으로 검색하는 메서드
     *
     * @param name 검색할 저자 이름
     * @param offset 페이지 위치
     * @param size 데이터 갯수
     * @return 저자 리스트
     */
    SearchedAuthorResponseDto searchAuthorByName(String name, int offset, int size);
}
