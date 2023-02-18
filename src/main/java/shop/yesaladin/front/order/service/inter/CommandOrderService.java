package shop.yesaladin.front.order.service.inter;

import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.order.dto.OrderCreateResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;
import shop.yesaladin.front.order.dto.OrderStatusChangeLogResponseDto;
import shop.yesaladin.front.order.dto.OrderStatusCode;

/**
 * 주문 생성과 관련한 service interface 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
public interface CommandOrderService {

    /**
     * 회원의 주문을 생성합니다.
     *
     * @param request 회원 주문 생성을 위한 데이터
     * @return 생성된 회원 주문
     * @author 최예린
     * @since 1.0
     */
    ResponseDto<OrderCreateResponseDto> createMemberOrder(OrderMemberCreateRequestDto request);

    /**
     * 회원의 주문을 숨김처리합니다.
     *
     * @param orderId 숨길 주문 pk
     * @param hidden  숨김 여부
     * @return 숨김 성공 여부
     * @author 최예린
     * @since 1.0
     */
    boolean hideOrder(Long orderId, boolean hidden);

    /**
     * 주문 상태 로그를 추가합니다.
     *
     * @param orderId 주문 아이디
     * @param code    주문 상태
     * @return 상태로그dto
     * @author 배수한
     * @since 1.0
     */
    ResponseDto<OrderStatusChangeLogResponseDto> appendOrderStatusCode(Long orderId, OrderStatusCode code);
}
