package shop.yesaladin.front.order.service.inter;


import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.order.dto.OrderSummaryResponseDto;

/**
 * 주문 조회 서비스 인터페이스
 *
 * @author 배수한
 * @since 1.0
 */
public interface QueryOrderService {

    PaginatedResponseDto<OrderSummaryResponseDto> getOrderListInPeriodByMemberId(
            Pageable pageable,
            String startDate,
            String endDate
    );

}
