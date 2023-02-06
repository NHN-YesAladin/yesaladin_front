package shop.yesaladin.front.order.service.inter;


import java.util.List;
import org.springframework.data.domain.Pageable;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.order.dto.OrderSheetResponseDto;
import shop.yesaladin.front.order.dto.OrderSummaryResponseDto;

/**
 * 주문 조회 서비스 인터페이스
 *
 * @author 배수한
 * @author 최예린
 * @since 1.0
 */
public interface QueryOrderService {

    /**
     * 회원 주문 전체 조회시, 사용하는 메서드 authentication header 유저 검증으로 인해 member id 사용하지 않아도 됨
     *
     * @param pageable   페이징 처리시 사용
     * @param requestDto 조회 시작, 마지막 일자 지정된 dto
     * @return 페이지네이션 되는 주문 조회 정보
     */
    PaginatedResponseDto<OrderSummaryResponseDto> getOrderListInPeriodByMemberId(
            Pageable pageable,
            PeriodQueryRequestDto requestDto
    );

    /**
     * 주문을 하기 위해 주문서에 필요한 데이터를 조회합니다.
     *
     * @param isbn     상품 isbn 리스트
     * @param quantity 상품 수량 리스트
     * @return 주문서에 필요한 데이터
     * @author 최예린
     * @since 1.0
     */
    ResponseDto<OrderSheetResponseDto> getOrderSheetData(List<String> isbn, List<String> quantity);
}
