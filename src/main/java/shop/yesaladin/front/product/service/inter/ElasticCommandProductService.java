package shop.yesaladin.front.product.service.inter;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 엘라스틱서치에 상품을 수정, 삭제하는 서비스 인터페이스
 *
 * @author 김선홍
 * @since 1.0
 */
public interface ElasticCommandProductService {

    /**
     * 업데이트 메서드
     *
     * @param id 업데이트할 상품의 id
     * @author 김선홍
     * @since 1.0
     */
    void update(Long id) throws JsonProcessingException;

    /**
     * 판매 여부 상태 변경 메서드
     *
     * @param id 수정할 상품의 id
     * @author 김선홍
     * @since 1.0
     */
    void changeIsSale(Long id);

    /**
     * 강제 품절 상태 변경 메서드
     *
     * @param id 수정할 상품의 id
     * @author 김선홍
     * @since 1.0
     */
    void changeIsForcedOutOfStock(Long id);

    /**
     * 상품 삭제 메서드
     *
     * @param id 삭제할 상품의 id
     * @author 김선홍
     * @since 1.0
     */
    void delete(Long id);
}
