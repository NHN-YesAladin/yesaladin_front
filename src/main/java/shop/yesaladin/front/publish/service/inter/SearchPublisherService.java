package shop.yesaladin.front.publish.service.inter;

import shop.yesaladin.front.publish.dto.SearchPublisherResponseDto;

/**
 * 엘라스틱 서치 출판사 검색 서비스 인터페이스
 *
 * @author : 김선홍
 * @since : 1.0
 */
public interface SearchPublisherService {
    SearchPublisherResponseDto searchPublisherByName(String name, int offset, int size);
}
