package shop.yesaladin.front.wishlist.service.inter;

import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.wishlist.dto.WishlistResponseDto;

/**
 * 위시리스트를 가져오는 서비스 인터페이스
 *
 * @author 김선홍
 * @since 1.0
 */
public interface QueryWishlistService {

    /**
     * 위시리스트를 구하는 메서드
     *
     * @param pageable 페이지 정보
     * @return 위시리스트
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<WishlistResponseDto> getWishlist(Pageable pageable);
}
