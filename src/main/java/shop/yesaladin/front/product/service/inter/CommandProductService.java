package shop.yesaladin.front.product.service.inter;

import java.io.IOException;
import shop.yesaladin.front.product.dto.ProductResponseDto;

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
     * @param productResponseDto 상품 등록 요청 Dto
     * @return 등록 후 응답받은 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    long register(ProductResponseDto productResponseDto) throws IOException;

//    void modify(ProductResponseDto productResponseDto, long productId);

    /**
     * 상품 삭제를 요청합니다.
     *
     * @param productId 삭제할 상품의 Id
     * @author 이수정
     * @since 1.0
     */
    void softDelete(long productId);
}
