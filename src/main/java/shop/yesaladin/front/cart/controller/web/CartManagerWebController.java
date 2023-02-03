package shop.yesaladin.front.cart.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 장바구니 추가 관련 페이지를 위한 Controller 입니다.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping
public class CartManagerWebController {


    /**
     * [POST /carts] 장바구니에 상품을 추가합니다.
     *
     * @return 상품을 추가하고
     */
    @PostMapping("/carts")
    public String addToCart() {
        return null;
    }
}
