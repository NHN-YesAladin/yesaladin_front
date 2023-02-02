package shop.yesaladin.front.writing.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 저자 리스트와 총 갯수
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@AllArgsConstructor
public class SearchedAuthorResponseDto {
    private Long count;
    private List<SearchedAuthorDto> searchedAuthorDtoList;
}
