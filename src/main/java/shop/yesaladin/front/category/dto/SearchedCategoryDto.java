package shop.yesaladin.front.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 정보 Dto
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedCategoryDto {

    private Long id;
    private String name;
    private String parentName;
}
