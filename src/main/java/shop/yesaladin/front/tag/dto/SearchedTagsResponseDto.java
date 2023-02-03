package shop.yesaladin.front.tag.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 태그 리스트와 총 갯수
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedTagsResponseDto {
    private Long count;
    private List<SearchedTagDto> searchedTagDtoList;
}
