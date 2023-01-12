package shop.yesaladin.front.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 카테고리의 일부 데이터를 back server 에게 전달받기 위한 dto
 *
 * @author 배수한
 * @since 1.0
 */

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private Long id;

    private String name;

    private Boolean isShown;

    private Integer order;

    private Long parentId;

    private String parentName;


}
