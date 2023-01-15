package shop.yesaladin.front.manager.product.service.inter;

import java.util.List;
import shop.yesaladin.front.manager.product.dto.ProductTypeResponseDto;

/**
 * 상품 유형 조회 요청을 위한 service 구현체 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryProductTypeService {

    List<ProductTypeResponseDto> findAll();
}
