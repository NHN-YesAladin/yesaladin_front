package shop.yesaladin.front.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 정보 Dto
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchedCategoryDto {
    Long id;
    String name;
    String parentName;
}
