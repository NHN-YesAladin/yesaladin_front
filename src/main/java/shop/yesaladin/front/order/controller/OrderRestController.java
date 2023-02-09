package shop.yesaladin.front.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.order.dto.OrderCreateResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;
import shop.yesaladin.front.order.service.inter.CommandOrderService;

/**
 * 주문의 rest controller입니다.
 *
 * @author 최예린
 * @since 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderRestController {

    private final CommandOrderService commandOrderService;

    /**
     * 회원의 주문을 생성합니다.
     *
     * @param request 회원의 주문 생성에 필요한 데이터
     * @return 생성된 회원의 주문
     * @author 최예린
     * @since 1.0
     */
    @PostMapping("/member")
    public ResponseDto<OrderCreateResponseDto> createMemberOrder(
            @RequestBody OrderMemberCreateRequestDto request
    ) {
        return commandOrderService.createMemberOrder(request);
    }

}
