package shop.yesaladin.front.product.service.inter;

import shop.yesaladin.front.product.dto.ProductCreateRequestDto;
import shop.yesaladin.front.product.dto.ProductModifyRequestDto;

import java.io.IOException;

/**
 * 상품 등록/수정/삭제 요청을 위한 Service Interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface CommandProductService {

    /**
     * 상품을 등록을 요청하여 등록된 상품정보를 반환합니다.
     *
     * @param createRequestDto 상품 등록 요청 Dto
     * @return 등록 후 응답받은 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    long register(ProductCreateRequestDto createRequestDto) throws IOException;

    /**
     * 상품을 수정을 요청하여 수정된 상품정보를 반환합니다.
     *
     * @param modifyRequestDto 상품 수정 요청 Dto
     * @param productId        수정할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    void modify(ProductModifyRequestDto modifyRequestDto, long productId) throws IOException;

    /**
     * 상품 삭제를 요청합니다.
     *
     * @param productId 삭제할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    void softDelete(long productId);

    /**
     * 상품 판매여부 변경을 요청합니다.
     *
     * @param productId 판매여부 변경할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    void changeIsSale(long productId);

    /**
     * 상품 강제품절여부 변경을 요청합니다.
     *
     * @param productId 강제품절여부 변경할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    void changeIsForcedOutOfStock(long productId);
}
