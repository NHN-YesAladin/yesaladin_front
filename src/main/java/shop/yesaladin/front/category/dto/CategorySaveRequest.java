package shop.yesaladin.front.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 카테고리 생성을 위한 Dto
 *
 * parentId가 null일 경우 상위 카테고리
 * @author 배수한
 * @since 1.0
 */

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveRequest {

    @NotBlank
    private String name;

    @NotNull
    private Boolean isShown;

    private Integer parentId;
}
