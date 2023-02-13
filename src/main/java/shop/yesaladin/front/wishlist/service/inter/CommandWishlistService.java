package shop.yesaladin.front.wishlist.service.inter;

/**
 * 위시리스트 서비스 인터페이스
 *
 * @since 1.0
 * @author 김선홍
 */
public interface CommandWishlistService {

    /**
     * 위시리스트 등록 메서드
     *
     * @param productId 위시리스트에 등록할 상품의 id
     * @return 상세보기로 돌아갈 화면
     * @since 1.0
     * @author 김선홍
     */
    Long save(Long productId);

    /**
     * 위시리스트 삭제 메서드
     *
     * @param productId 위시리스트에서 삭제할 상품의 id
     * @since 1.0
     * @author 김선홍
     */
    void delete(Long productId);

    /**
     * 위시리스트 존재 확인 메서드
     *
     * @param productId 확인할 상품의 id 키
     * @return 반환 유무(유: true, 무: false)
     * @since 1.0
     * @author 김선홍
     */
    Boolean isExist(Long productId);
}
