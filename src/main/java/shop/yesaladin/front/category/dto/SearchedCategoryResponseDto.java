package shop.yesaladin.front.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 카테고리 리스트와 총 갯수
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedCategoryResponseDto {
    Long count;
    List<SearchedCategoryDto> searchedCategoryDtoList;
}
