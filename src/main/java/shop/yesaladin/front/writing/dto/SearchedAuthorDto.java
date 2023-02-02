package shop.yesaladin.front.writing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 저자 정보 dto
 *
 * @since : 1.0
 * @author : 김선홍
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchedAuthorDto {
    private Long id;
    private String name;
    private String loginId;
}
