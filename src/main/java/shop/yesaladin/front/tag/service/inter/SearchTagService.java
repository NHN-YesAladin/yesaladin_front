package shop.yesaladin.front.tag.service.inter;

import shop.yesaladin.front.tag.dto.SearchedTagsResponseDto;

/**
 * 엘라스틱 서치 태그 검색 서비스 인터페이스
 *
 * @author : 김선홍
 * @since : 1.0
 */
public interface SearchTagService {

    /**
     * 이름으로 저자를 검색하는 메서드
     *
     * @param name 검색할 저자의 이름
     * @param offset 페이지 위치
     * @param size 데이터 갯수
     * @return 조회된 저자 리스트
     * @author : 김선홍
     * @since : 1.0
     */
    SearchedTagsResponseDto searchTagByName(String name, int offset, int size);
}
