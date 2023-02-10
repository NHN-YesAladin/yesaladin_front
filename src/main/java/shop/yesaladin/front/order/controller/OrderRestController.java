package shop.yesaladin.front.order.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.yesaladin.front.order.dto.OrderStatusCode;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

/**
 * 주문 관련 Rest Controller 자바스크립트 fetch 통신을 보조하는 용으로 사용
 *
 * @author 배수한
 * @since 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final QueryOrderService queryOrderService;

    /**
     * 마이페이지 - 주문 상태의 주문 개수를 디스플레이 하기 위해 조회
     *
     * @return 주문 상태 & 주문 상태에 따른 주문 개수
     */
    @GetMapping("/status-count")
    public Map<OrderStatusCode, Long> getMyOrderCount() {
        return queryOrderService.getOrderCountByStatus();
    }
}
