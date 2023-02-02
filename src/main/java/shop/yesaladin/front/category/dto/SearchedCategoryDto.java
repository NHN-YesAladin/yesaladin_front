package shop.yesaladin.front.category.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카테고리 정보 Dto
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@AllArgsConstructor
public class SearchedCategoryDto {
    private Long id;
    private String name;
    private String parentName;
}
