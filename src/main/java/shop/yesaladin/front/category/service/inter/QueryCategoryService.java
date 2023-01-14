package shop.yesaladin.front.category.service.inter;

import java.awt.print.Pageable;
import java.util.List;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;

/**
 * 카테고리 조회를 위한 기능을 가지는 서비스
 *
 * @author 배수한
 * @since 1.0
 */
public interface QueryCategoryService {

    /**
     * 모든 1차 카테고리를 조회하는 기능
     *
     * @return 카테고리의 일부 정보를 담고있는 dto 리스트
     */
    List<CategoryResponseDto> getParentCategories();

    /**
     * 1차 카테고리 id를 통해서 자식 카테고리(=2차 카테고리)를 페이징하여 조회하는 기능
     *
     * @param pageRequestDto page와 size를 담고있는 dto
     * @param parentId 1차 카테고리 id
     * @return 페이징 정보 및 데이터 리스트를 담고있는 dto
     */
    PaginatedResponseDto<CategoryResponseDto> getChildCategoriesByParentId(
            PageRequestDto pageRequestDto,
            Long parentId
    );

}
