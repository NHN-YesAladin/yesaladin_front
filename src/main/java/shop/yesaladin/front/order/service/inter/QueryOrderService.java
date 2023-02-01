package shop.yesaladin.front.order.service.inter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;

/**
 * @author 배수한
 * @since 1.0
 */
public class QueryOrderService {

    PaginatedResponseDto<OrderSummaryResponseDto> getOrderListInPeriodByMemberId(
            PeriodQueryRequestDto queryDto,
            long memberId,
            Pageable pageable
    );
}
