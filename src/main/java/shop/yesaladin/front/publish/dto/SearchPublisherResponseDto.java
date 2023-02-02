package shop.yesaladin.front.publish.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 출판사 정보 dto 리스트와 총 갯수
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPublisherResponseDto {
    private Long count;
    private List<SearchedPublisherDto> searchedPublisherDtoList;
}
