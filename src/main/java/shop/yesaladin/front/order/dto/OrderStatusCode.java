package shop.yesaladin.front.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 주문 상태 코드 Enum : 마이페이지 - 주문상태에서 사용
 *
 * @author 배수한
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum OrderStatusCode {
    ORDER(1, "입금/결제 대기"),
    DEPOSIT(2, "결제 완료"),
    READY(3, "배송 대기"),
    DELIVERY(4, "배송중"),
    COMPLETE(5, "배송 완료"),
    CONFIRM(6, "주문 확정"),
    REFUND(7, "환불"),
    CANCEL(8, "취소");

    private final int statusCode;
    private final String koName;


    /**
     * 상태 코드를 통해 주문상태 코드를 찾아냅니다.
     *
     * @param status status 상태 숫자값
     * @return 주문상태코드
     */
    public static OrderStatusCode getOrderStatusCodeByNumber(Long status) {
        return Arrays.stream(OrderStatusCode.values())
                .filter(c -> c.getStatusCode() == status)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException());
    }

}
