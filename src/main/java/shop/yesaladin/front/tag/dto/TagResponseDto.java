package shop.yesaladin.front.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 태그 조회를 받아와 저장한 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {

    private int id;
    private String name;
}
