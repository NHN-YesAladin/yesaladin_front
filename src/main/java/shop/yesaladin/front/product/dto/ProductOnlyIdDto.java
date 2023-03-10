package shop.yesaladin.front.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품의 아이디만 반환받는 경우 사용하는 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnlyIdDto {

    private long id;
}
