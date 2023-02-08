package shop.yesaladin.front.publish.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 출판사 정보 dto 리스트와 총 갯수
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPublisherResponseDto {
    private Long count;
    private List<SearchedPublisherDto> searchedPublisherDtoList;
}
