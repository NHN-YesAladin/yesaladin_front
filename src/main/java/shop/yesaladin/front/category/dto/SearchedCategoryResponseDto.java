package shop.yesaladin.front.category.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카테고리 리스트와 총 갯수
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@AllArgsConstructor
public class SearchedCategoryResponseDto {
    private Long count;
    private List<SearchedCategoryDto> searchedCategoryDtoList;
}
