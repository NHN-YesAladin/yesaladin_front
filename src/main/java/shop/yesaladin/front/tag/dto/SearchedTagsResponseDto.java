package shop.yesaladin.front.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 태그 리스트와 총 갯수
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchedTagsResponseDto {
    private Long count;
    private List<SearchedTagDto> searchedTagDtoList;
}
