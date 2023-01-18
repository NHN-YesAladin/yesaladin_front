package shop.yesaladin.front.product.service.inter;

import java.io.IOException;
import shop.yesaladin.front.product.dto.ProductResponseDto;

/**
 * 상품 등록/수정/삭제 요청을 위한 service interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface CommandProductService {


    long register(ProductResponseDto productResponseDto) throws IOException;

    void modify(ProductResponseDto productResponseDto, long productId);

    void softDelete(long productId);
}
