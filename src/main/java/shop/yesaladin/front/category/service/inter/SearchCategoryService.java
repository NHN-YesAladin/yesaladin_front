package shop.yesaladin.front.category.service.inter;

import shop.yesaladin.front.category.dto.SearchedCategoryResponseDto;

/**
 * 상품 등록 용 카테고리 검색 서비스 인터페이스
 *
 * @author : 김선홍
 * @since : 1.0
 */
public interface SearchCategoryService {
    /**
     * 카테고리 이름으로 검색하는 메서드
     *
     * @return 검색된 카테고리 리스트와 총 갯수
     * @author : 김선홍
     * @since : 1.0
     */
    SearchedCategoryResponseDto searchCategoryByName(String name, int offset, int size);
}
