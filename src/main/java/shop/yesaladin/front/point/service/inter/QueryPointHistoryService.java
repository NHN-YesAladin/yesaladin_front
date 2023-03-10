package shop.yesaladin.front.point.service.inter;

import org.springframework.data.domain.Pageable;
import shop.yesaladin.common.dto.ResponseDto;
import shop.yesaladin.front.common.dto.PaginatedResponseDto;
import shop.yesaladin.front.point.dto.PointHistoryResponseDto;

/**
 * 포인트 이용 내역을 조회하기 위한 service 입니다.
 *
 * @author 최예린
 * @since 1.0
 */
public interface QueryPointHistoryService {

    /**
     * 회원의 사용 또는 적립 포인트 내역을 조회합니다.
     *
     * @param pageable page와 size를 담은 객체
     * @param code     포인트 사용/적립 구분
     * @return 회원의 포인트 내역
     * @author 최예린
     * @since 1.0
     */
    ResponseDto<PaginatedResponseDto<PointHistoryResponseDto>> getPointHistories(
            Pageable pageable,
            String code
    );

    /**
     * 회원의 전체 포인트 내역을 조회합니다.
     *
     * @param pageable page와 size를 담은 객체
     * @return 회원의 포인트 내역
     * @author 최예린
     * @since 1.0
     */
    ResponseDto<PaginatedResponseDto<PointHistoryResponseDto>> getAllPointHistories(
            Pageable pageable
    );

    /**
     * 회원의 포인트를 조회합니다.
     *
     * @return 회원의 포인트
     * @author 최예린
     * @since 1.0
     */
    long getMemberPoint();
}
