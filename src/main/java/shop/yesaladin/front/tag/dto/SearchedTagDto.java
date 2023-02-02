package shop.yesaladin.front.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 태그 정보 dto
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedTagDto {
    private Long id;
    private String name;
}
