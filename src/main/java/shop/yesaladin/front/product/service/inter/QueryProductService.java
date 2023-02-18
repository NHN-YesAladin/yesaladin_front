package shop.yesaladin.front.product.service.inter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.product.dto.*;

import java.util.List;
import java.util.Set;

/**
 * 상품 조회 요청을 위한 Service Interface 입니다.
 *
 * @author 이수정
 * @author 김선홍
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

    /**
     * 연관 상품 등록을 위한 상품을 검색하는 메서드
     *
     * @param id       메인 상품의 id
     * @param title    검색할 제목
     * @param pageable 요청 페이지 정보
     * @return 조회된 상품 리스트
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<RelationsResponseDto> findProductByTitle(
            Long id,
            String title,
            Pageable pageable
    );

    /**
     * 신간 상품 조회 메서드
     *
     * @param pageable 페이지 정보
     * @return 신간 상품 리스트
     * @author 김선홍
     * @since 1.0
     */
    List<ProductRecentResponseDto> findRecentProduct(Pageable pageable);

    /**
     * 최근 본 상품 조회 메서드
     *
     * @param dto 최근 본 상품 요청 dto
     * @param pageable       페이지 정보
     * @return 최근 본 상품의 정보 리스트
     * @author 김선홍
     * @since 1.0
     */
    PaginatedResponseDto<ProductRecentResponseDto> findRecentViewProduct(
            RecentViewProductRequestDto dto,
            Pageable pageable
    ) throws JsonProcessingException;
}
