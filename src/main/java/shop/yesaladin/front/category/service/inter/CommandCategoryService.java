package shop.yesaladin.front.category.service.inter;


import shop.yesaladin.front.category.dto.*;

/**
 * Create, Update, Delete 를 controller 단에서 사용하기 위해 서비스 인터페이스
 *
 * @author 배수한
 * @since 1.0
 */

public interface CommandCategoryService {

    CategoryDto create(CategoryCreateRequest createRequest);

}
