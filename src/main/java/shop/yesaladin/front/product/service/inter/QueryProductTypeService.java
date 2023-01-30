package shop.yesaladin.front.product.service.inter;

import shop.yesaladin.front.product.dto.ProductTypeResponseDto;

import java.util.List;

/**
 * 상품 유형 조회 요청을 위한 Service Interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryProductTypeService {

    /**
     * 상품 유형 전체 조회를 요청하여 응답을 받습니다.
     *
     * @return 응답받은 전체 조회된 Dto list
     * @author 이수정
     * @since 1.0
     */
    List<ProductTypeResponseDto> findAll();
}
