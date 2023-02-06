package shop.yesaladin.front.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니에 담을 상품의 Id와 개수, 상품의 종류를 담은 Dto 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {

    private String id;
    private int quantity;

    private Boolean isEbook;
    private Boolean isSubscriptionAvailable;
}
