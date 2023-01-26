package shop.yesaladin.front.order.controller.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.order.dto.TotalOrderResponseDto;
import shop.yesaladin.front.point.dto.PointHistoryResponseDto;

/**
 * @author 배수한
 * @since 1.0
 */

@Controller
@RequestMapping("/mypage/orders")
public class OrderMyPageWebController {

    @GetMapping
    public String getOrderList(
            @PageableDefault Pageable pageable,
//            Authentication authentication,
            Model model
    ) {
//        String loginId = ((UserDetails) authentication.getPrincipal()).getUsername();

        //TODO orderService 구현 및 선언 -> 페이지네이션된 주문 리스트 조회
        List<TotalOrderResponseDto> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(TotalOrderResponseDto.builder()
                    .orderId((long) i)
                    .orderNumber("00000000000" + i)
                    .orderMember("김씨")
                    .orderName("자바 외 2")
                    .orderDate(LocalDate.now())
                    .categoryCount(i)
                    .productCount(i)
                    .amount(10000L * i)
                    .deliveryStatus("배송완료")
                    .build());
        }
        PaginatedResponseDto<TotalOrderResponseDto> response = PaginatedResponseDto.<TotalOrderResponseDto>builder()
                .totalPage(10L)
                .currentPage(0L)
                .totalDataCount(100L)
                .dataList(dataList)
                .build();


        model.addAttribute("currentPage", response.getCurrentPage());
        model.addAttribute("totalPage", response.getTotalPage());
        model.addAttribute("totalDataCount", response.getTotalDataCount());
        model.addAttribute("dataList", response.getDataList());
        model.addAttribute("code", null);

        return "mypage/order/order-list-view";
    }
}
