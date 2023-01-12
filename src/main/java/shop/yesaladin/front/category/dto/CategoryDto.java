package shop.yesaladin.front.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * service에서 return 값으로 전달 해주는 Dto
 * parent 관련 정보는 nullable
 * @author 배수한
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    private Integer parentId;
    private String parentName;

}
