package shop.yesaladin.front.writing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 저자 리스트와 총 갯수
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedAuthorResponseDto {
    private Long count;
    private List<SearchedAuthorDto> searchedAuthorDtoList;
}
