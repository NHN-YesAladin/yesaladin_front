package shop.yesaladin.front.order.service.inter;

import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.order.dto.OrderCreateResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;

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
}