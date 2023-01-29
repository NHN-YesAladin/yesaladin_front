package shop.yesaladin.front.product.service.inter;

import shop.yesaladin.front.product.dto.SearchProductRequestDto;
import shop.yesaladin.front.product.dto.SearchedProductResponseDto;


/**
 * 상품 검색 서비스 인터페이스
 *
 * @author : 김선홍
 * @since : 1.0
 */
public interface SearchProductService {

    /**
     * 상품의 필드를 이용해 검색하는 메서드
     *
     * @param requestDto 검색 조건, 내용, 페이징 dto
     * @return 검색된 리스트
     * @author : 김선홍
     * @since : 1.0
     */
    SearchedProductResponseDto searchProductsByProductField(SearchProductRequestDto requestDto);
}
