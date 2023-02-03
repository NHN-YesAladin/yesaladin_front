package shop.yesaladin.front.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 태그 정보 dto
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedTagDto {
    private Long id;
    private String name;
}
