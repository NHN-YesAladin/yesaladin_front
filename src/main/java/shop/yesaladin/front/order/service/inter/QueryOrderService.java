package shop.yesaladin.front.order.service.inter;


import org.springframework.data.domain.Pageable;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.BestsellerResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.common.dto.PeriodQueryRequestDto;
import shop.yesaladin.front.coupon.dto.CouponOrderSheetRequestDto;
import shop.yesaladin.front.coupon.dto.CouponOrderSheetResponseDto;
import shop.yesaladin.front.order.dto.*;
import shop.yesaladin.front.statistics.dto.SalesStatisticsResponseDto;

import java.util.List;
import java.util.Map;

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
     * @author 배수한
     * @since 1.0
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

    /**
     * 주문상태에 따른 회원 주문 조회
     *
     * @param pageable 페이징 처리시 사용
     * @param status   주문 상태 번호
     * @return 페이징 된 주문 조회 정보
     */
    PaginatedResponseDto<OrderStatusResponseDto> getOrderListByOrderStatus(
            Pageable pageable,
            Long status
    );

    /**
     * 주문 상테에 따른 주문 개수 조회
     *
     * @return 주문 상태 & 주문 개수
     * @author 배수한
     * @since 1.0
     */
    Map<OrderStatusCode, Long> getOrderCountByStatus();

    /**
     * 주문 상세 조회
     *
     * @param orderNumber 주문 번호
     * @return 주문 상세 조회 결과
     * @author 배수한
     * @since 1.0
     */
    OrderDetailsResponseDto getOrderDetailsDtoByOrderNumber(String orderNumber, String type);

    /**
     * 상품에 쿠폰을 적용합니다.
     *
     * @param request 상품과 쿠폰 정보
     * @return 할인된 상품 정보
     * @author 최예린
     * @since 1.0
     */
    CouponOrderSheetResponseDto getDiscountPrice(CouponOrderSheetRequestDto request);

    /**
     * 기간에 따른 매출 통계 정보를 반환받습니다.
     *
     * @param pageable 페이징을 위한 Pageable
     * @param start    시작일
     * @param end      종료일
     * @return 매출 통계 정보
     * @author 이수정
     * @since 1.0
     */
    PaginatedResponseDto<SalesStatisticsResponseDto> getSalesStatistics(Pageable pageable, String start, String end);

    /**
     * 매출을 기반으로 베스트셀러를 조회하여 반환받습니다.
     *
     * @return 조회된 베스트셀러
     * @author 이수정
     * @since 1.0
     */
    List<BestsellerResponseDto> getBestSeller();
}
