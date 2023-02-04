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

    /**
     * 회원 주문 전체 조회시, 사용하는 메서드
     *  authentication header 유저 검증으로 인해 member id 사용하지 않아도 됨
     *
     * @param pageable 페이징 처리시 사용
     * @param requestDto 조회 시작, 마지막 일자 지정된 dto
     * @return 페이지네이션 되는 주문 조회 정보
     */
    PaginatedResponseDto<OrderSummaryResponseDto> getOrderListInPeriodByMemberId(
            Pageable pageable,
            PeriodQueryRequestDto requestDto
    );

}
