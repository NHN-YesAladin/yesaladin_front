package shop.yesaladin.front.manager.product.service.inter;

import java.util.Map;
import shop.yesaladin.front.manager.product.dto.ProductDetailResponseDto;

/**
 * 상품 조회 요청을 위한 service interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryProductService {

    ProductDetailResponseDto getProductDetail(long productId);

    Map<String, Object> getProductRelatedDtoMap();
}
