package shop.yesaladin.front.category.service.inter;


import shop.yesaladin.front.category.dto.CategoryResponseDto;
import shop.yesaladin.front.category.dto.CategorySaveRequestDto;

/**
 * 카테고리 생성,수정,삭제를 위한 기능을 가지는 서비스
 *
 * @author 배수한
 * @since 1.0
 */

public interface CommandCategoryService {

    /**
     * 카테고리 생성 기능
     *
     * @param createRequest 이름, 노출여부, 부모id가 있는 dto
     * @return CategoryResponseDto 카테고리의 일부 정보가 있는 dto
     */
    CategoryResponseDto create(CategorySaveRequestDto createRequest);

    /**
     * 카테고리 수정 기능
     *
     * @param id            수정하고자 하는 카테고리의 id
     * @param modifyRequest 이름, 노출여부, 부모id가 있는 dto
     * @return CategoryResponseDto 카테고리의 일부 정보가 있는 dto
     */
    CategoryResponseDto modify(Long id, CategorySaveRequestDto modifyRequest);

    /**
     * 카테고리 삭제 기능
     *
     * @param id 삭제하고자 하는 카테고리 id
     */
    void delete(Long id);
}
