package shop.yesaladin.front.category.service.inter;


import shop.yesaladin.front.category.dto.*;

/**
 * Create, Update, Delete 를 controller 단에서 사용하기 위해 서비스 인터페이스
 *
 * @author 배수한
 * @since 1.0
 */

public interface CommandCategoryService {

    CategoryResponseDto create(CategorySaveRequest createRequest);

    CategoryResponseDto modify(Long parentId, CategorySaveRequest modifyRequest);

    void delete(Long id);
}
