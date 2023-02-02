package shop.yesaladin.front.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
