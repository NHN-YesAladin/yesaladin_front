package shop.yesaladin.front.writing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 저자 정보 dto
 *
 * @author : 김선홍
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedAuthorDto {
    private Long id;
    private String name;
    private String loginId;
}
