package shop.yesaladin.front.order.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.coupon.dto.CouponOrderSheetRequestDto;
import shop.yesaladin.front.coupon.dto.CouponOrderSheetResponseDto;
import shop.yesaladin.front.order.dto.OrderCreateResponseDto;
import shop.yesaladin.front.order.dto.OrderMemberCreateRequestDto;
import shop.yesaladin.front.order.dto.OrderStatusCode;
import shop.yesaladin.front.order.service.inter.CommandOrderService;
import shop.yesaladin.front.order.service.inter.QueryOrderService;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 주문의 rest controller입니다.
 *
 * @author 최예린
 * @author 배수한
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final QueryOrderService queryOrderService;
    private final CommandOrderService commandOrderService;


    /**
     * 마이페이지 - 주문 상태의 주문 개수를 디스플레이 하기 위해 조회
     *
     * @return 주문 상태 & 주문 상태에 따른 주문 개수
     */
    @GetMapping("/status-count")
    public Map<OrderStatusCode, Long> getMyOrderCount() {
        return queryOrderService.getOrderCountByStatus();

    }

    /**
     * 상품에 쿠폰을 적용합니다.
     *
     * @param request 상품과 쿠폰 데이터
     * @return 할인된 상품 데이터
     * @author 최예린
     * @since 1.0
     */
    @GetMapping("/coupons")
    public CouponOrderSheetResponseDto getDiscountPrice(
            @ModelAttribute
            CouponOrderSheetRequestDto request
    ) {
        log.info("request : {}", request);
        return queryOrderService.getDiscountPrice(request);
    }

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
            @Valid @RequestBody OrderMemberCreateRequestDto request,
            @RequestParam(value = "type", required = false) String type,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseDto.<OrderCreateResponseDto>builder()
                    .success(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .errorMessages(bindingResult.getAllErrors()
                            .stream()
                            .map(error -> error.getDefaultMessage())
                            .collect(Collectors.toList()))
                    .build();
        }

        ResponseDto<OrderCreateResponseDto> responseDto = commandOrderService.createMemberOrder(
                request,
                type
        );
        return ResponseDto.<OrderCreateResponseDto>builder()
                .status(HttpStatus.CREATED)
                .success(responseDto.isSuccess())
                .data(responseDto.getData())
                .errorMessages(responseDto.getErrorMessages())
                .build();
    }
}
