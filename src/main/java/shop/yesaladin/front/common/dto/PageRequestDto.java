package shop.yesaladin.front.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * pagination을 간편하게 하기 위한 dto
 *
 * @author 배수한
 * @since 1.0
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {

    private Integer page;
    private Integer size;

}
