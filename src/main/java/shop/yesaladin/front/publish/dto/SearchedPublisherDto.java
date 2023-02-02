package shop.yesaladin.front.publish.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 출판사 정보 dto
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedPublisherDto {
    private Long id;
    private String name;
}
