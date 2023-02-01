package shop.yesaladin.front.publish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 출판사 정보 dto
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class SearchedPublisherDto {
    Long id;
    String name;
}
