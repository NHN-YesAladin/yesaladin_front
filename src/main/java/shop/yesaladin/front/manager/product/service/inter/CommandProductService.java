package shop.yesaladin.front.manager.product.service.inter;

import shop.yesaladin.front.manager.product.dto.ProductRequestedDto;

/**
 * 상품 등록/수정/삭제 요청을 위한 service interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface CommandProductService {


    long register(ProductRequestedDto productRequestedDto);
}
