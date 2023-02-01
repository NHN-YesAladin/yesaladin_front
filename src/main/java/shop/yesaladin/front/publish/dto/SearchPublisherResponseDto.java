package shop.yesaladin.front.publish.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 출판사 정보 dto 리스트와 총 갯수
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@AllArgsConstructor
public class SearchPublisherResponseDto {
    Long count;
    List<SearchedPublisherDto> searchedPublisherDtoList;
}
