package shop.yesaladin.front.category.service.impl;

import shop.yesaladin.front.category.dto.CategoryCreateRequest;
import shop.yesaladin.front.category.dto.CategoryDto;
import shop.yesaladin.front.category.service.inter.CommandCategoryService;

/**
 * @author 배수한
 * @since 1.0
 */
public class CommandCategoryServiceImpl implements CommandCategoryService {

    @Override
    public CategoryDto create(CategoryCreateRequest createRequest) {

        return new CategoryDto();
    }
}
