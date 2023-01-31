package shop.yesaladin.front.product.service.inter;

import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.RelationsResponseDto;

/**
 * 상품 연관관계 조회 요청을 위한 Service Interface 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
public interface QueryRelationService {


    /**
     * 상품 연관관계 조회를 요청하여 응답을 받습니다.
     *
     * @param productId 조회하고자 하는 상품의 Id
     * @return 응답받은 상품의 연관관계 정보를 담은 Dto List
     * @author 이수정
     * @since 1.0
     */
    PaginatedResponseDto<RelationsResponseDto> getRelations(long productId, PageRequestDto pageRequestDto);
}
