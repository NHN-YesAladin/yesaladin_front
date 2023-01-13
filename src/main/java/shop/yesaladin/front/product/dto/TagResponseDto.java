package shop.yesaladin.front.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 태그 조회를 받아와 저장한 dto
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TagResponseDto {
    private int id;
    private String name;
}
