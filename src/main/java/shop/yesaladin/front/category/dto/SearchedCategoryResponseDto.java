package shop.yesaladin.front.category.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 리스트와 총 갯수
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedCategoryResponseDto {

    private Long count;
    private List<SearchedCategoryDto> searchedCategoryDtoList;
}
