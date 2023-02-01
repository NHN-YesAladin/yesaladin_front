package shop.yesaladin.front.writing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 저자 정보 dto
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@AllArgsConstructor
public class SearchedAuthorDto {
    Long id;
    String name;
    String loginId;
}
