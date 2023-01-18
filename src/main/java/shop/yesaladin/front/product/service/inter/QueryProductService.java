package shop.yesaladin.front.product.service.inter;

import java.util.List;
import java.util.Map;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.ProductsResponseDto;

/**
 * 상품 조회 요청을 위한 service interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryProductService {

    ProductDetailResponseDto getProductDetail(long productId);

    Map<String, Object> getProductRelatedDtoMap();

    PaginatedResponseDto<ProductsResponseDto> findAll(PageRequestDto pageRequestDto, Integer typeId);
}
