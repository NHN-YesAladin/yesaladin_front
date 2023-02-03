package shop.yesaladin.front.product.service.inter;

import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.ProductDetailResponseDto;
import shop.yesaladin.front.product.dto.ProductModifyInitDto;
import shop.yesaladin.front.product.dto.ProductsResponseDto;

/**
 * 상품 조회 요청을 위한 Service Interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryProductService {

    /**
     * 상품 상세 조회를 요청하여 응답을 받습니다.
     *
     * @param productId 찾고자 하는 상품의 Id
     * @return 응답받은 상품 상세 정보를 담은 Dto
     * @author 이수정
     * @since 1.0
     */
    ProductDetailResponseDto getProductDetail(long productId);

    /**
     * 관리자용 상품 전체 조회를 요청하여 응답받습니다.
     *
     * @param pageRequestDto Pagination을 위한 Dto
     * @param typeId         상품 유형 id
     * @return 응답받은 상품 전체 조회 Dto
     * @author 이수정
     * @since 1.0
     */
    PaginatedResponseDto<ProductsResponseDto> findAllForManager(
            PageRequestDto pageRequestDto,
            Integer typeId
    );

    /**
     * 모든 사용자용 상품 전체 조회를 요청하여 응답받습니다.
     *
     * @param pageRequestDto Pagination을 위한 Dto
     * @param typeId         상품 유형 id
     * @return 응답받은 상품 전체 조회 Dto
     * @author 이수정
     * @since 1.0
     */
    PaginatedResponseDto<ProductsResponseDto> findAll(
            PageRequestDto pageRequestDto,
            Integer typeId
    );

    /**
     * 상품 수정 View를 위한 정보를 요청하여 응답받습니다.
     *
     * @param productId 정보를 조회할 상품의 Id
     * @return 응답받은 상품 조회 Dto
     * @author 이수정
     * @since 1.0
     */
    ProductModifyInitDto getProductForForm(String productId);
}
