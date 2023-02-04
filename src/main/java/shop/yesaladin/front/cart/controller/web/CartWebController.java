package shop.yesaladin.front.cart.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
@Controller
@RequestMapping("/cart")
public class CartWebController {


    /**
     * [GET /cart] 장바구니 View 를 반환합니다.
     *
     * @return 장바구니 View
     * @author 이수정
     * @since 1.0
     */
    @GetMapping
    public String cart() {
        return "main/cart/cart";
    }


    /**
     * [POST /cart] 장바구니에 상품을 추가합니다.
     *
     * @author 이수정
     * @since 1.0
     */
    @PostMapping
    public void addToCart() {

    }
}
