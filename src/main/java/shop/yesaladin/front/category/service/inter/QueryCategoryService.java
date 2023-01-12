package shop.yesaladin.front.category.service.inter;

import java.awt.print.Pageable;
import java.util.List;
import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.common.dto.PageRequestDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;

/**
 * @author 배수한
 * @since 1.0
 */
public interface QueryCategoryService {

    List<CategoryResponseDto> getParentCategories();

    PaginatedResponseDto<CategoryResponseDto> getChildCategoriesByParentId(
            PageRequestDto pageRequestDto,
            Long parentId
    );

}
